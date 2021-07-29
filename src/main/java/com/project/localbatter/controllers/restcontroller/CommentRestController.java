package com.project.localbatter.controllers.restcontroller;

import com.project.localbatter.dto.CommentDTO;
import com.project.localbatter.services.CommentService;
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
