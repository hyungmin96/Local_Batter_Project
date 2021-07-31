package com.project.localbatter.api.group;

import com.project.localbatter.entity.GroupCommentEntity;
import com.project.localbatter.services.GroupBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import com.project.localbatter.dto.*;
import com.project.localbatter.services.GroupCommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/group/comment")
public class GroupCommentApiController {

    private final GroupBoardService groupBoardService;
    private final GroupCommentService groupCommentService;

    @PostMapping("/board/comment/write")
    public ResponseEntity<GroupCommentDTO> commentWrite(GroupCommentDTO groupCommentDTO){
        groupCommentService.commentWrite(groupCommentDTO);
        return new ResponseEntity<>(groupCommentDTO, HttpStatus.OK);
    }

    @PostMapping("/comment/update")
    public ResponseEntity<GroupCommentDTO> updateComment(GroupCommentDTO groupCommentDTO){
        return groupCommentService.updateComment(groupCommentDTO);
    }

    @PostMapping("/comment/delete")
    public ResponseEntity<GroupCommentDTO> deleteComment(GroupCommentDTO groupCommentDTO){
        return groupCommentService.deleteComment(groupCommentDTO);
    }

    @GetMapping("/comment/get_latest_comments")
    public Page<GroupCommentEntity> getLatestComments(GroupBoardDTO groupBoardDTO){
        return groupBoardService.getLatestComments(groupBoardDTO);
    }

}
