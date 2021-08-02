package com.project.localbatter.services.group;

import com.project.localbatter.dto.GroupCommentDTO;
import com.project.localbatter.entity.GroupBoardEntity;
import com.project.localbatter.entity.GroupCommentEntity;
import com.project.localbatter.entity.GroupUserJoinEntity;
import com.project.localbatter.repositories.GroupBoardRepository;
import com.project.localbatter.repositories.GroupCommentRepository;
import com.project.localbatter.repositories.GroupUserJoinQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GroupCommentService {

    private final GroupCommentRepository groupCommentRepository;
    private final GroupBoardRepository groupBoardRepository;
    private final GroupUserJoinQueryRepository groupUserJoinQuseryRepository;

    public ResponseEntity<GroupCommentDTO> updateComment(GroupCommentDTO groupCommentDTO){
        GroupCommentEntity groupCommentEntity = groupCommentRepository.getOne(groupCommentDTO.getCommentId());
        groupCommentEntity.update(groupCommentDTO.getComment());
        groupCommentDTO.setResult("Success");
        groupCommentRepository.save(groupCommentEntity);
        return new ResponseEntity<>(groupCommentDTO, HttpStatus.OK);
    }

    public ResponseEntity<GroupCommentDTO> deleteComment(GroupCommentDTO groupCommentDTO){
        groupCommentRepository.deleteById(groupCommentDTO.getCommentId());
        return new ResponseEntity<>(groupCommentDTO, HttpStatus.OK);
    }

    public GroupCommentEntity commentWrite(GroupCommentDTO groupCommentDTO){

        GroupUserJoinEntity groupUserJoinEntity = groupUserJoinQuseryRepository
                .findGroupUserJoinEntity(groupCommentDTO.getUserId(), groupCommentDTO.getGroupId());
        GroupBoardEntity groupBoardEntity = groupBoardRepository.getOne(groupCommentDTO.getBoardId());

        groupCommentDTO.setGroupId(groupCommentDTO.getGroupId());
        GroupCommentEntity groupCommentEntity = groupCommentDTO.toEntity(groupBoardEntity, groupUserJoinEntity);
        groupCommentRepository.save(groupCommentEntity);
        return groupCommentEntity;
    }
}
