package com.imageupload.example.services;

import com.imageupload.example.entity.CommentEntity;
import com.imageupload.example.repositories.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class ProfileService {
    
    @Autowired
    private CommentRepository commentRepository;

    public Page<CommentEntity> getAllCommentEntities(int page, int display){

        UserDetails user = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        PageRequest request = PageRequest.of(page, display, Sort.Direction.DESC, "id");

        return commentRepository.findAllBytarget(user.getUsername(), request);

    }

}
