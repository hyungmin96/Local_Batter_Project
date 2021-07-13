package com.imageupload.example.controllers.restcontroller;

import java.io.IOException;
import java.security.Principal;
import com.imageupload.example.entity.CommentEntity;
import com.imageupload.example.entity.ProfileEntity;
import com.imageupload.example.entity.UserEntity;
import com.imageupload.example.services.ProfileService;
import com.imageupload.example.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
