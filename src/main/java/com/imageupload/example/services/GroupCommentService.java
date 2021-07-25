package com.imageupload.example.services;

import com.imageupload.example.dto.GroupCommentDTO;
import com.imageupload.example.entity.GroupBoardEntity;
import com.imageupload.example.entity.GroupCommentEntity;
import com.imageupload.example.entity.GroupUsersEntity;
import com.imageupload.example.repositories.GroupBoardRepository;
import com.imageupload.example.repositories.GroupCommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
@RequiredArgsConstructor
public class GroupCommentService {

    private final GroupCommentRepository groupCommentRepository;
    private final GroupBoardRepository groupBoardRepository;
    private final HttpSession session;


    public ResponseEntity<GroupCommentDTO> deleteComment(GroupCommentDTO groupCommentDTO){
        groupCommentRepository.deleteById(groupCommentDTO.getCommentId());
        return new ResponseEntity<>(groupCommentDTO, HttpStatus.OK);
    }

    public void commentWrite(GroupCommentDTO groupCommentDTO){
        GroupUsersEntity groupUsersEntity = (GroupUsersEntity) session.getAttribute("group_user_entity");
        GroupBoardEntity groupBoardEntity = groupBoardRepository.getOne(groupCommentDTO.getBoardId());

        groupCommentDTO.setGroupId(groupCommentDTO.getGroupId());
        groupCommentDTO.setGroupBoard(groupBoardEntity);
        groupCommentDTO.setWriter(groupUsersEntity);

        GroupCommentEntity groupCommentEntity = groupCommentDTO.toEntity();
        groupCommentRepository.save(groupCommentEntity);
        groupCommentDTO.setCommentId(groupCommentEntity.getCommentId());
    }
}
