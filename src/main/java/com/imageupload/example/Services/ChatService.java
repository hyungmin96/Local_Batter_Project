package com.imageupload.example.services;

import java.time.LocalDateTime;

import com.imageupload.example.vo.MessageVo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class ChatService {
    
    @Autowired
    private SimpMessagingTemplate simpMessageTemplate;

    public void sendNotification(@Payload MessageVo Message){

        Message = MessageVo.builder()
        .sendUser("sendUser")
        .notificationType("notificationType")
        .message("message")
        .date(LocalDateTime.now()).targetUser("targetUser")
        .build();

        simpMessageTemplate.convertAndSend("/notification/" + Message.getTargetUser(), Message);
    }

}
