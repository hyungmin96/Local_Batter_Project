package com.imageupload.example.controllers.chatcontroller;

import java.security.Principal;
import com.imageupload.example.services.ChatService;
import com.imageupload.example.vo.MessageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ChatController {
    
    @Autowired
    private ChatService chatService;

    @GetMapping("/{userId}/chatlist")
    public String getChatList(){
        return "/chat/chatRoomList";
    }

    @GetMapping("/api/chat/target={target}/room={roomid}")
    public String getChatRoom(Model model, 
                            @PathVariable String target, @PathVariable long roomid){

        model.addAttribute("target", target);
        model.addAttribute("roomid", roomid);

        return "/chat/chatroom";
    }


    
    @MessageMapping("/send/chat")    
    public void sendMessage(Principal principal, MessageVo message){
        chatService.sendMessage(principal, message);
    }

}
