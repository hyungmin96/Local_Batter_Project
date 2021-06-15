package com.imageupload.example.controllers.sitecontroller;

import java.security.Principal;

import com.imageupload.example.entity.UserEntity;
import com.imageupload.example.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class UserSiteController {

    @Autowired
    private UserService userService;

    @GetMapping("/profile/user={nickname}")
    public String getProfile(Model model, @PathVariable String nickname){
        UserEntity userEntity = userService.findUserOne(nickname);
        if(userEntity != null){
            model.addAttribute("profile", userEntity.getProfile());
            return "/user/profile";
        }
        return "/error/errorpage";
    }

    @GetMapping("/user/login")
    public String login(Model model){
        return "/user/login";
    }

    @GetMapping("/user/join")
    public String join(){
        return "/user/join";
    }
    
}
