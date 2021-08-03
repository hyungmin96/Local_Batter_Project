package com.project.localbatter.services.group;

import com.project.localbatter.components.GenerateFile;
import com.project.localbatter.components.PagingUtil;
import com.project.localbatter.dto.*;
import com.project.localbatter.entity.GroupEntity;
import com.project.localbatter.entity.GroupUserJoinEntity;
import com.project.localbatter.entity.UserEntity;
import com.project.localbatter.repositories.*;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import static com.project.localbatter.entity.QGroupEntity.groupEntity;
import static com.project.localbatter.entity.QGroupUserJoinEntity.groupUserJoinEntity;

@Service
@RequiredArgsConstructor
public class GroupService {

    private final UserRepository userRepository;
    private final GroupRepository groupRepository;
    private final GroupUserJoinRepository groupUserJoinRepository;
    private final GroupUserJoinQueryRepository groupUserJoinQueryRepository;
    private final JPAQueryFactory queryFactory;
    private final PagingUtil pagingUtil;
    private final GenerateFile generateFile;

    public GroupEntity updateGroup(GroupCreateDTO groupCreateDTO){
        GroupEntity groupEntity = groupRepository.getOne(groupCreateDTO.getGroupId());
        if(groupCreateDTO.getFiles() != null){
            List<GenerateFileDTO> files = generateFile.createFile(groupCreateDTO.getFiles());
            groupCreateDTO.setFilePath(files.get(0).getName());
        }
        groupEntity.updateInfo(groupCreateDTO.getTitle(), groupCreateDTO.getDescription(), groupCreateDTO.getFilePath());
        groupRepository.save(groupEntity);
        return groupEntity;
    }

    public GroupEntity deleteGroup(GroupPageDTO groupPageDTO){
        GroupEntity groupEntity = groupRepository.getOne(groupPageDTO.getGroupId());
        groupRepository.delete(groupEntity);
        return groupEntity;
    }

    public Page<GroupMemberDTO> getMemberList(GroupPageDTO groupPageDTO){

        PageRequest pageRequest = PageRequest.of(groupPageDTO.getPage(), groupPageDTO.getDisplay());

        JPAQuery<GroupMemberDTO> query = queryFactory
                .select(Projections.fields(GroupMemberDTO.class,
                    groupUserJoinEntity.user.id.as("userId"),
                    groupUserJoinEntity.user.profilePath.as("profile"),
                    groupUserJoinEntity.user.username.as("username")))
                .from(groupUserJoinEntity)
                .where(groupUserJoinEntity.group.id.eq(groupPageDTO.getGroupId()));

        return pagingUtil.getPageImpl(pageRequest, query, GroupUserJoinEntity.class);

    }

    public ResponseEntity<String> enterGroup(GroupPageDTO groupPageDTO) {
        UserEntity userEntity = userRepository.getOne(groupPageDTO.getUserId());
        GroupEntity groupEntity = groupRepository.getOne(groupPageDTO.getGroupId());
        GroupUserJoinEntity groupUserJoinEntity = GroupUserJoinEntity.builder().user(userEntity).group(groupEntity).type(GroupUserJoinEntity.userAuthority.member).build();
        groupUserJoinRepository.save(groupUserJoinEntity);
        groupRepository.memberCountUp(groupEntity.getId());
        return new ResponseEntity<String>("", HttpStatus.OK);
    }

    public ResponseEntity<String> exitGroup(GroupPageDTO groupPageDTO) {
        GroupUserJoinEntity groupUserJoinEntity = groupUserJoinQueryRepository.findGroupUserJoinEntity(groupPageDTO.getUserId(), groupPageDTO.getGroupId());
        groupUserJoinRepository.delete(groupUserJoinEntity);
        groupRepository.memberCountDown(groupPageDTO.getGroupId());
        return new ResponseEntity<String>("", HttpStatus.OK);
    }

    @Transactional(readOnly = true)
    public List<GroupPageDTO> getGroupList(GroupPageDTO groupPageDTO) {
        Pageable page = PageRequest.of(groupPageDTO.getPage(), groupPageDTO.getDisplay());
        List<GroupEntity> items = queryFactory
                .selectDistinct(groupEntity)
                .from(groupEntity)
                .orderBy(groupEntity.id.desc())
                .offset(page.getOffset())
                .limit(page.getPageSize())
                .fetch();
        return items.stream().map(GroupPageDTO::new).collect(Collectors.toList());
    }

    public ResponseEntity<GroupCreateDTO> createGroupRoom(GroupCreateDTO groupCreateDTO) {
        UserEntity userEntity = userRepository.getOne(groupCreateDTO.getUserId());
        List<GenerateFileDTO> generateGroupFiles = generateFile.createFile(groupCreateDTO.getFiles());
        groupCreateDTO.setFiles(null);
        groupCreateDTO.setFilePath(generateGroupFiles.get(0).getName());
        GroupEntity groupEntity = groupCreateDTO.toEntity();
        groupRepository.save(groupEntity);
        GroupUserJoinEntity groupUserJoinEntity = GroupUserJoinEntity.builder().user(userEntity).group(groupEntity).type(GroupUserJoinEntity.userAuthority.manager).build();
        groupUserJoinRepository.save(groupUserJoinEntity);
        groupRepository.memberCountUp(groupEntity.getId());

        return new ResponseEntity<>(groupCreateDTO, HttpStatus.OK);
    }

    public ResponseEntity<String> isMember(Long userId, Long groupId) {
        Long isMemberCheck = queryFactory
                .select(groupUserJoinEntity.id)
                .from(groupUserJoinEntity)
                .where(groupUserJoinEntity.user.id.eq(userId)
                .and(groupUserJoinEntity.group.id.eq(groupId)))
                .fetchOne();

        if (isMemberCheck == null)
            return new ResponseEntity<String>("result: don't exist member List", HttpStatus.OK);
        else
            return new ResponseEntity<String>("result: isMember", HttpStatus.OK);
    }
}
