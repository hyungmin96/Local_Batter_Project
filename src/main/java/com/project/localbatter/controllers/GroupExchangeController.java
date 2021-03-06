package com.project.localbatter.controllers;

import com.project.localbatter.dto.Group.GroupBoardDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class GroupExchangeController {

    @GetMapping("/view/client/confirm/exchange/{exchangeId}")
    public String getClientExhcangeConfirmView(Model model, @PathVariable Long exchangeId){
        model.addAttribute("exchangeId", exchangeId);
        return "/exchange/clientConfirmRequestView";
    }

    @GetMapping("/view/exchange/chat/list")
    public String getExchangeChatList(){
        return "/exchange/exchangeChat";
    }

    @GetMapping("/view/client/request/{clientId}")
    public String getClientExchange(Model model, @PathVariable Long clientId){
        model.addAttribute("clientId", clientId);
        return "/exchange/clientRequestView";
    }

    @GetMapping("/view/transaction/my/boards")
    public String getTransactionBoards(){
        return "/exchange/exchangeList";
    }

    @GetMapping("/")
    public String getHome(){
        return "/board/home";
    }
    
    @GetMapping("/writer/map")
    public String getWriterMap(){
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
