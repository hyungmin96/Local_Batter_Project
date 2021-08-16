package com.project.localbatter.api;

import com.project.localbatter.services.UserService;
import com.project.localbatter.dto.UserDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class UserApiController {
    
    private final UserService userService;

    @PostMapping("/api/login")
    public void login(){

    }

    @PostMapping("/api/join")
    public String join(@RequestBody UserDTO vo){
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
