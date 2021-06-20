package com.imageupload.example.repositories;

import com.imageupload.example.entity.CommentEntity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
    
}
