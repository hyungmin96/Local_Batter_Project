package com.imageupload.example.Controllers.RestController;

import com.imageupload.example.Services.UserService;
import com.imageupload.example.Vo.Role;
import com.imageupload.example.Vo.UserVo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestControllers {
    
    @Autowired
    private UserService userService;

    @PostMapping("/api/login")
    public String login(@RequestBody UserVo vo){
        userService.loadUserByUsername(vo.getUsername());
        return "로그인 성공";
    }

    @PostMapping("/api/join")
    public String join(@RequestBody UserVo vo){
        vo.setRole(Role.ROLE_USER);
        userService.userSave(vo);
        return "회원가입 성공";
    }

    @PostMapping("/api/checkUserName")
    public boolean duplicatedItemCheck(@RequestParam String username){
        return userService.checkUserName(username);
    }

}
