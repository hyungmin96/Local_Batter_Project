package com.project.localbatter.services.group;

import com.project.localbatter.api.group.GroupApiController;
import com.project.localbatter.components.GenerateFile;
import com.project.localbatter.components.PagingUtil;
import com.project.localbatter.dto.GenerateFileDTO;
import com.project.localbatter.dto.Group.GroupCreateDTO;
import com.project.localbatter.dto.Group.GroupMemberDTO;
import com.project.localbatter.dto.Group.GroupPageDTO;
import com.project.localbatter.entity.GroupEntity;
import com.project.localbatter.entity.GroupUserJoinEntity;
import com.project.localbatter.entity.UserEntity;
import com.project.localbatter.repositories.GroupRepository;
import com.project.localbatter.repositories.GroupUserJoinQueryRepository;
import com.project.localbatter.repositories.GroupUserJoinRepository;
import com.project.localbatter.repositories.UserRepository;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static com.project.localbatter.entity.QGroupEntity.groupEntity;
import static com.project.localbatter.entity.QGroupUserJoinEntity.groupUserJoinEntity;
import static com.project.localbatter.entity.QUserEntity.userEntity;

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
                        userEntity.id.as("userId"),
                        userEntity.profilePath.as("profile"),
                        userEntity.username.as("username")))
                .from(groupUserJoinEntity)
                .join(groupUserJoinEntity.user, userEntity)
                .where(groupUserJoinEntity.group.id.eq(groupPageDTO.getGroupId()));

        return pagingUtil.getPageImpl(pageRequest, query, query.fetchCount(),GroupUserJoinEntity.class);
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
    public Page<GroupEntity> getGroupList(GroupPageDTO groupPageDTO) {
        Pageable page = PageRequest.of(groupPageDTO.getPage(), groupPageDTO.getDisplay());
        return groupRepository.findByJoinGroupList(groupPageDTO.getUserId(), page);
    }

    @Transactional(readOnly = true)
    public List<GroupEntity> getRandomGroupList(GroupPageDTO groupPageDTO) {
        long totalElementCount = queryFactory.selectFrom(groupEntity).fetchCount();
        int totalPages = (int)(totalElementCount / groupPageDTO.getDisplay());
        int randomPageIndex = (totalPages > 0) ? new Random().nextInt(totalPages) : 0;
        Pageable page = PageRequest.of(randomPageIndex, 30);
        List<GroupEntity> items = new ArrayList<>(groupRepository.findByRandomGroup(page).getContent());
        Collections.shuffle(items);
        return items;
    }

    public ResponseEntity<GroupCreateDTO> createGroupRoom(GroupCreateDTO groupCreateDTO) {
        UserEntity userEntity = userRepository.getById(groupCreateDTO.getUserId());
        List<GenerateFileDTO> generateGroupFiles = generateFile.createFile(groupCreateDTO.getFiles());
        if(generateGroupFiles.size() > 0){
            groupCreateDTO.setFiles(null);
            groupCreateDTO.setFilePath(generateGroupFiles.get(0).getName());
        }
        GroupEntity groupEntity = groupCreateDTO.toEntity();
        groupRepository.save(groupEntity);
        GroupUserJoinEntity groupUserJoinEntity = GroupUserJoinEntity.builder().user(userEntity).group(groupEntity).type(GroupUserJoinEntity.userAuthority.manager).build();
        groupUserJoinRepository.save(groupUserJoinEntity);
        groupRepository.memberCountUp(groupEntity.getId());
        return new ResponseEntity<>(groupCreateDTO, HttpStatus.OK);
    }

    public GroupApiController.ResponseMemberCheckDTO isMember(Long userId, Long groupId) {
        return queryFactory
                .select(Projections.fields(GroupApiController.ResponseMemberCheckDTO.class,
                        userEntity.id.as("userId"),
                        groupUserJoinEntity.group.id.as("groupId"),
                        userEntity.username.as("username"),
                        userEntity.profilePath.as("profilePath"),
                        groupUserJoinEntity.type.as("type")))
                .from(groupUserJoinEntity)
                .join(groupUserJoinEntity.user, userEntity)
                .where(userEntity.id.eq(userId)
                .and(groupUserJoinEntity.group.id.eq(groupId)))
                .fetchOne();
    }
}
