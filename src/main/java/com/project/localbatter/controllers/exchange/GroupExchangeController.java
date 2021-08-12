package com.project.localbatter.controllers.exchange;

import com.project.localbatter.dto.Group.GroupBoardDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class GroupExchangeController {

    @GetMapping("/writer/map")
    public String getHome(){
        return "/exchange/writerMap";
    }

    // 게시글 작성 controller
    @GetMapping("/post/board")
    public String getRegistInfo(){
        return "/exchange/exchangeinfo";
    }

    @GetMapping("/request/exchange/{boardId}")
    public String getBoardInfo(Model model, GroupBoardDTO groupBoardDTO){
        model.addAttribute("board", groupBoardDTO.getBoardId());
        return "/exchange/clientmap";
    }

    @GetMapping("/get_request_list/{boardId}")
    public String getRequest(Model model, @PathVariable Long boardId){
        model.addAttribute("boardId", boardId);
        return "/exchange/requestlist";
    }

}
