package com.project.localbatter.services;

import com.project.localbatter.components.GenerateFile;
import com.project.localbatter.dto.GenerateFileDTO;
import com.project.localbatter.dto.GroupCreateDTO;
import com.project.localbatter.dto.GroupFileDTO;
import com.project.localbatter.dto.GroupPageDTO;
import com.project.localbatter.entity.GroupEntity;
import com.project.localbatter.entity.GroupUserJoinEntity;
import com.project.localbatter.entity.UserEntity;
import com.project.localbatter.repositories.GroupFileRepository;
import com.project.localbatter.repositories.GroupRepository;
import com.project.localbatter.repositories.GroupUserJoinRepository;
import com.project.localbatter.repositories.UserRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
    private final GroupFileRepository groupFileRepository;
    private final GroupRepository groupRepository;
    private final GroupUserJoinRepository groupUserJoinRepository;
    private final JPAQueryFactory queryFactory;
    private final GenerateFile generateFile;
    private static Logger log = LogManager.getLogger();

    public ResponseEntity<String> enterGroup(GroupPageDTO groupPageDTO){
        UserEntity userEntity = userRepository.getOne(groupPageDTO.getUserId());
        GroupEntity groupEntity = groupRepository.getOne(groupPageDTO.getGroupId());
        GroupUserJoinEntity groupUserJoinEntity = GroupUserJoinEntity.builder().user(userEntity).group(groupEntity).build();
        groupUserJoinRepository.save(groupUserJoinEntity);
        groupRepository.memberCountUp(groupEntity.getId());
        return new ResponseEntity<String>("", HttpStatus.OK);
    }

    public ResponseEntity<String> exitGroup(GroupPageDTO groupPageDTO){
        UserEntity userEntity = userRepository.getOne(groupPageDTO.getUserId());
        GroupEntity groupEntity = groupRepository.getOne(groupPageDTO.getGroupId());
        groupUserJoinRepository.deleteByGroup(userEntity, groupEntity);
        groupRepository.memberCountDown(groupEntity.getId());
        return new ResponseEntity<String>("", HttpStatus.OK);
    }

    @Transactional(readOnly = true)
    public List<GroupPageDTO> getGroupList(GroupPageDTO groupPageDTO){
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

    public ResponseEntity<GroupCreateDTO> createGroupRoom(GroupCreateDTO groupCreateDTO){

        UserEntity userEntity = userRepository.getOne(groupCreateDTO.getUserId());
        GroupEntity groupEntity = groupCreateDTO.toEntity();
        groupRepository.save(groupEntity);

        GroupUserJoinEntity groupUserJoinEntity = GroupUserJoinEntity.builder()
                .group(groupEntity)
                .user(userEntity)
                .build();
        groupUserJoinRepository.save(groupUserJoinEntity);
        groupRepository.memberCountUp(groupEntity.getId());

            List<GenerateFileDTO> generateGroupFiles = generateFile.createFile(groupCreateDTO.getFiles());

            generateGroupFiles.stream().map(GroupFileDTO::new).collect(Collectors.toList())
                    .forEach(item -> groupFileRepository.save(item.toEntity(groupEntity)));

            groupCreateDTO.setFiles(null);

        return new ResponseEntity<>(groupCreateDTO, HttpStatus.OK);
    }

    @Transactional(readOnly = true)
    public GroupPageDTO getGroupInfo(GroupPageDTO groupPageDTO){
        GroupEntity groupEntity = groupRepository.findById(groupPageDTO.getUserId()).get();
        return new GroupPageDTO(groupEntity);
    }

    public ResponseEntity<String> isMember(Long userId, Long groupId){
        Long isMemberCheck = queryFactory
                    .select(groupUserJoinEntity.id)
                    .from(groupUserJoinEntity)
                    .where(groupUserJoinEntity.user.id.eq(userId)
                    .and(groupUserJoinEntity.group.id.eq(groupId)))
                    .fetchOne();

            if(isMemberCheck == null)
                return new ResponseEntity<String>("result: don't exist member List", HttpStatus.OK);
            else
                return new ResponseEntity<String>("result: isMember", HttpStatus.OK);
    }
}
