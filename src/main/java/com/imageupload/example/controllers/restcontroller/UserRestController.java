package com.imageupload.example.controllers.restcontroller;

import com.imageupload.example.services.UserService;

import java.security.Principal;

import com.imageupload.example.entity.UserEntity;
import com.imageupload.example.models.Role;
import com.imageupload.example.models.UserProfileInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserRestController {
    
    @Autowired
    private UserService userService;

    @PostMapping("/api/profile/save")
    public UserEntity saveProfile(Principal user, UserProfileInfo profile){
        
        UserEntity userEntity = userService.findUserOne(user.getName());

        if(userEntity != null){

            userEntity.setNickname(profile.getNickname());
            userEntity.setIntroduce(profile.getIntroduce());
            userEntity.setLocation(profile.getLocation());
            userEntity.setPreferTime(profile.getPreferTime());
            userEntity.setProfileImg(profile.getProfileImg());
            
            userService.userSave(userEntity);
            
        }

        return userEntity;
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
        vo.setRole(Role.ROLE_USER);
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
