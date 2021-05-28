package com.imageupload.example.Controllers.RestController;

import java.text.ParseException;

import com.imageupload.example.Components.boardServiceMethod.createTime;
import com.imageupload.example.Services.BoardService;
import com.imageupload.example.Services.UserService;
import com.imageupload.example.Vo.PageableVo;
import com.imageupload.example.Vo.Role;
import com.imageupload.example.Vo.UserVo;
import com.imageupload.example.Vo.boardVo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestControllers {
    
    @Autowired
    private UserService userService;

    @Autowired
    private BoardService boardService;

    @GetMapping({"/board/products/search={search}&display={display}&order={order}&page={page}"})
    public Page<boardVo> searchBoardsData(PageableVo pageVo){

        Page<boardVo> searchBoards;

        searchBoards = boardService.getBoardList(pageVo);

        searchBoards.forEach(action -> {
            try {
                action.setDisplayDate(new createTime(action.getCreateTime()).getTimeDiff());
            } catch (ParseException e) {
                e.printStackTrace();
            }
        });

        Integer[] pages = new Integer[searchBoards.getTotalPages()];
        for(int i = 0; i < pages.length; i ++){
            pages[i] = (i + 1);
        }

        return searchBoards;
        
    }

    @PostMapping("/api/login")
    public String login(@RequestBody UserVo vo){
        UserDetails account = userService.loadUserByUsername(vo.getUsername());
        if(account != null)
            return "로그인 성공";
        else
            return "로그인 실패";
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
