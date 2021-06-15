package com.imageupload.example.controllers.restcontroller;

import com.imageupload.example.services.UserService;

import java.io.IOException;
import java.security.Principal;

import com.imageupload.example.entity.ProfileEntity;
import com.imageupload.example.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class UserRestController {
    
    @Autowired
    private UserService userService;

    @PostMapping("/api/profile/save")
    public ResponseEntity<String> saveProfile(Principal user, @RequestParam("profileImg") MultipartFile file, ProfileEntity profile) throws IOException{
        UserEntity userEntity = userService.userUpdate(user, file, profile);
        return new ResponseEntity<String>(userEntity.getNickname(), HttpStatus.OK);
    }

    @PostMapping("/api/login")
    public String login(@RequestBody UserEntity vo){
        UserDetails account = userService.loadUserByUsername(vo.getUsername());
        if(account != null)
            return "로그인 성공";
        else
            return "로그인 실패";
    }

    @PostMapping("/api/join")
    public String join(@RequestBody UserEntity vo){
        userService.userSave(vo);
        return "회원가입 성공";
    }

    @PostMapping("/api/checkUserName")
    public boolean duplicatedItemCheck(@RequestParam String username){
        return userService.checkUserName(username);
    }

    @GetMapping("/login/error")
    public String loginError(){
        return "로그인 실패";
    }

}
