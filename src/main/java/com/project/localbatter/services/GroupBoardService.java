package com.project.localbatter.services;

import com.project.localbatter.components.DeleteFile;
import com.project.localbatter.components.GenerateFile;
import com.project.localbatter.components.PagingUtil;
import com.project.localbatter.dto.GenerateFileDTO;
import com.project.localbatter.dto.GroupBoardDTO;
import com.project.localbatter.dto.GroupBoardFileDTO;
import com.project.localbatter.entity.*;
import com.project.localbatter.repositories.*;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.project.localbatter.entity.QGroupBoardEntity.groupBoardEntity;
import static com.project.localbatter.entity.QGroupUserJoinEntity.groupUserJoinEntity;
import static com.project.localbatter.entity.QUserEntity.userEntity;

@Service
@RequiredArgsConstructor
@Transactional
public class GroupBoardService {

    private final GroupRepository groupRepository;
    private final UserRepository userRepository;
    private final GroupBoardRepository groupBoardRepository;
    private final GroupCommentRepository groupCommentRepository;
    private final GroupBoardFileRepository groupBoardFileRepository;
    private final GroupUserJoinRepository groupUserJoinRepository;
    private final GroupUserJoinQuseryRepository groupUserJoinQuseryRepository;
    private final GenerateFile generateFile;
    private final JPAQueryFactory queryFactory;
    private final PagingUtil pagingUtil;

    @Transactional(readOnly = true)
    public Page<GroupBoardEntity> getBoardList(Long groupId, PageRequest request){
        JPAQuery<GroupBoardEntity> query = queryFactory
                .selectDistinct(groupBoardEntity)
                .from(groupBoardEntity)
                .leftJoin(groupBoardEntity.groupUserJoinEntity, groupUserJoinEntity)
                .fetchJoin()
                .leftJoin(groupUserJoinEntity.user, userEntity)
                .fetchJoin()
                .where(groupUserJoinEntity.group.id.eq(groupId));

        return pagingUtil.getPageImpl(request, query, GroupBoardEntity.class);
    }

    public Page<GroupCommentEntity> getLatestComments(GroupBoardDTO groupBoardDTO){
        PageRequest request = PageRequest.of(groupBoardDTO.getPage(), groupBoardDTO.getDisplay(), Sort.Direction.DESC, "commentId");
        return groupCommentRepository.findTop5ByGroupId(groupBoardDTO.getGroupId(), request);
    }

    public Page<GroupBoardFileEntity> getLatestImages(GroupBoardDTO groupBoardDTO){
        PageRequest request = PageRequest.of(groupBoardDTO.getPage(), groupBoardDTO.getDisplay(), Sort.Direction.DESC, "id");
        return groupBoardFileRepository.findTop9BygroupId(groupBoardDTO.getGroupId(), request);
    }

    public ResponseEntity<GroupBoardDTO> updateNotice(GroupBoardDTO groupBoardDTO){
        // Check Authorization to enter user

//        if(isUserHasAuthority){
//            GroupBoardEntity groupBoardEntity = groupBoardRepository.getOne(groupBoardDTO.getBoardId());
//            groupBoardDTO.setBoardId(groupBoardEntity.getBoardId());
//            groupBoardEntity.updateNotice(groupBoardDTO.getType());
//            groupBoardRepository.save(groupBoardEntity);
//            groupBoardDTO.setResult("Success");
//
//            return new ResponseEntity<>(groupBoardDTO, HttpStatus.OK);
//        }
        groupBoardDTO.setResult("Failed - No request authority");
        return new ResponseEntity<>(groupBoardDTO, HttpStatus.BAD_REQUEST);
    }

    public Page<GroupBoardEntity> getNoticeList(GroupBoardDTO groupBoardDTO){
        PageRequest request = PageRequest.of(groupBoardDTO.getPage(), groupBoardDTO.getDisplay(), Sort.Direction.DESC, "boardId");
        return groupBoardRepository.findAllBygroupIdAndType(groupBoardDTO.getGroupId(), GroupBoardEntity.BoardType.notice, request);
    }

    public void delete(GroupBoardDTO groupBoardDTO){
        GroupBoardEntity groupBoardEntity = groupBoardRepository.getOne(groupBoardDTO.getBoardId());
        String[] filePathArray = groupBoardEntity.getFiles().stream().map(GroupBoardFileEntity::getName).toArray(String[]::new);
        new DeleteFile(filePathArray).deleteFile();
        groupBoardRepository.deleteById(groupBoardDTO.getBoardId());
    }

    public GroupBoardEntity post(GroupBoardDTO groupBoardDTO){

        GroupUserJoinEntity groupUserJoinEntity = groupUserJoinQuseryRepository.findGroupUserJoinEntity(groupBoardDTO.getUserId(), groupBoardDTO.getGroupId());

        groupBoardDTO.setGroupUserJoin(groupUserJoinEntity);
        GroupBoardEntity groupBoardEntity = groupBoardDTO.toEntity();
        groupBoardRepository.save(groupBoardEntity);

        List<GenerateFileDTO> groupBoardFiles = generateFile.createFile(groupBoardDTO.getBoard_img());

        groupBoardFiles.stream().map(GroupBoardFileDTO::new).collect(Collectors.toList())
                .forEach(item -> {
                    GroupBoardFileEntity file = item.toEntity(groupBoardEntity);
                    groupBoardDTO.addFile(file);
                    groupBoardFileRepository.save(file);
                });

        return groupBoardEntity;
    }
}
