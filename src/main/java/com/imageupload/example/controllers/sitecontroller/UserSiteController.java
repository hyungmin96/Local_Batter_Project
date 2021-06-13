package com.imageupload.example.controllers.sitecontroller;

import java.security.Principal;

import com.imageupload.example.entity.UserEntity;
import com.imageupload.example.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserSiteController {

    @Autowired
    private UserService userService;

    @GetMapping("/user/mypage")
    public String getProfile(Model model, Principal user){

        UserEntity userEntity = userService.findUserOne(user.getName());

        if(userEntity != null){

            model.addAttribute("profile", userEntity);

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
