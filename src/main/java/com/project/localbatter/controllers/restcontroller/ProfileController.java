package com.project.localbatter.controllers.restcontroller;

import java.io.IOException;
import java.security.Principal;
import com.project.localbatter.entity.CommentEntity;
import com.project.localbatter.entity.ProfileEntity;
import com.project.localbatter.entity.UserEntity;
import com.project.localbatter.services.ProfileService;
import com.project.localbatter.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/profile")
@RequiredArgsConstructor
public class ProfileController {
    
    private final ProfileService profileService;
    private final UserService userService;

    @PostMapping("/save")
    public ResponseEntity<String> saveProfile(Principal user, @RequestParam(required = false) MultipartFile[] profileImg, ProfileEntity profile) throws IOException{
        UserEntity userEntity = userService.userUpdate(user, profileImg, profile);
        return new ResponseEntity<String>(userEntity.getUsername(), HttpStatus.OK);
    }

    @GetMapping("/comments")
    public Page<CommentEntity> getComments(@RequestParam int page, @RequestParam int display){
        return profileService.getAllCommentEntities(page, display);
    }

}
