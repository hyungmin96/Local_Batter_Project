package com.project.localbatter.services;

import com.project.localbatter.dto.GroupCommentDTO;
import com.project.localbatter.entity.GroupBoardEntity;
import com.project.localbatter.entity.GroupCommentEntity;
import com.project.localbatter.repositories.GroupBoardRepository;
import com.project.localbatter.repositories.GroupCommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GroupCommentService {

    private final GroupCommentRepository groupCommentRepository;
    private final GroupBoardRepository groupBoardRepository;

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

    public void commentWrite(GroupCommentDTO groupCommentDTO){
//        GroupBoardEntity groupBoardEntity = groupBoardRepository.getOne(groupCommentDTO.getBoardId());
//        groupCommentDTO.setGroupId(groupCommentDTO.getGroupId());
//        GroupCommentEntity groupCommentEntity = groupCommentDTO.toEntity();
//        groupCommentRepository.save(groupCommentEntity);
//        groupCommentDTO.setCommentId(groupCommentEntity.getCommentId());
    }
}
