package com.project.localbatter.controllers;

import com.project.localbatter.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class UserController {

    final private UserService userService;

    @GetMapping("/my/profile/{userId}")
    public String getProfile(Model model, @PathVariable Long userId){
        return "/user/profile";
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
