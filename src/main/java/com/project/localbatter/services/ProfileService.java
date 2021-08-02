package com.project.localbatter.services;

import com.project.localbatter.entity.CommentEntity;
import com.project.localbatter.repositories.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileService {
    
    private final CommentRepository commentRepository;

    public Page<CommentEntity> getAllCommentEntities(int page, int display){
        UserDetails user = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        PageRequest request = PageRequest.of(page, display, Sort.Direction.DESC, "id");
        return commentRepository.findAllBytarget(user.getUsername(), request);
    }

}
