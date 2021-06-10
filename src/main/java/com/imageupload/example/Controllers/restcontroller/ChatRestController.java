package com.imageupload.example.controllers.restcontroller;

import java.time.LocalDateTime;
import com.imageupload.example.models.UserVo;
import com.imageupload.example.services.ChatService;
import com.imageupload.example.vo.MessageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ChatRestController {
    
    @Autowired
    private ChatService chatService;

    @PostMapping("/create/room")
    public MessageVo createRoom(@AuthenticationPrincipal UserVo principal, 
        @RequestParam("target") String targetString, 
        @RequestParam("user") String user){

        MessageVo message = MessageVo.builder()
        .result("success")
        .notificationType("notificationType")
        .sendUser(principal.getUsername())
        .message("message")
        .targetUser(targetString)
        .date(LocalDateTime.now())
        .build();

        chatService.sendNotification(message);

        return message;
    }   

}
