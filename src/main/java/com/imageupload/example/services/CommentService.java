package com.imageupload.example.services;

import com.imageupload.example.entity.CommentEntity;
import com.imageupload.example.repositories.CommentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {
    
    @Autowired
    private CommentRepository commentRepository;

    public void saveEntity(CommentEntity commentEntity){
        commentRepository.save(commentEntity);
    }

    public float getMannerScore(String target){
        return commentRepository.findAllBymannerScore(target);
    }

}
