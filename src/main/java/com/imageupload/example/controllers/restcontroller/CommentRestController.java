package com.imageupload.example.controllers.restcontroller;

import com.imageupload.example.dto.CommentDTO;
import com.imageupload.example.dto.GroupCommentDTO;
import com.imageupload.example.services.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/comment")
@RequiredArgsConstructor
public class CommentRestController {

    private final CommentService commentService;

    @PostMapping("/write")
    public ResponseEntity<String> setComment(CommentDTO commentDTO){
        return commentService.saveEntity(commentDTO);
    }

}
