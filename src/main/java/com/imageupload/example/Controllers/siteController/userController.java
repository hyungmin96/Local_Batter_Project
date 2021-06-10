package com.imageupload.example.controllers.sitecontroller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class userController {

    @GetMapping("/user/login")
    public String login(Model model){

        return "/user/login";
    }

    @GetMapping("/user/join")
    public String join(){

        return "/user/join";
    }
    
}
