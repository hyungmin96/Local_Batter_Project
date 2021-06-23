package com.imageupload.example.controllers.restcontroller;

import com.imageupload.example.entity.CommentEntity;
import com.imageupload.example.services.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {
    
    @Autowired
    private ProfileService profileService;

    @GetMapping("/comments")
    public Page<CommentEntity> getComments(@RequestParam int page, @RequestParam int display){
        return profileService.getAllCommentEntities(page, display);
    }

}
