package com.project.localbatter.services;

import com.project.localbatter.components.GenerateFile;
import com.project.localbatter.dto.*;
import com.project.localbatter.entity.*;
import com.project.localbatter.repositories.*;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

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
    private Logger log = LogManager.getLogger();

    @Transactional(readOnly = true)
    public List<GroupPageDTO> getGroupList(GroupPageDTO groupPageDTO){

        Pageable page = PageRequest.of(groupPageDTO.getPage(), groupPageDTO.getDisplay());

       List<GroupEntity> items = queryFactory
                        .selectDistinct(groupEntity)
                        .from(groupEntity)
                        .orderBy(groupEntity.id.asc())
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

        if(groupCreateDTO.getFiles() != null && groupCreateDTO.getFiles().length > 0){
            GenerateFile generateGroupFiles = new GenerateFile(groupCreateDTO.getFiles());
            List<GenerateFileDTO> files = generateGroupFiles.createFile();

            for(GenerateFileDTO file : files){
                GroupFileDTO groupFileDTO = new GroupFileDTO();
                groupFileDTO.setName(file.getFileName());
                groupFileDTO.setPath(file.getPath());
                groupFileRepository.save(groupFileDTO.toEntity(groupEntity));
            }
            groupCreateDTO.setFiles(null);
        }
        return new ResponseEntity<>(groupCreateDTO, HttpStatus.OK);
    }

    @Transactional(readOnly = true)
    public GroupPageDTO getGroupInfo(GroupPageDTO groupPageDTO){
        GroupEntity groupEntity = groupRepository.findById(groupPageDTO.getId()).get();
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
