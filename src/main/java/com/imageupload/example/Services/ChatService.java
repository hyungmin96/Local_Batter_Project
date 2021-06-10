package com.imageupload.example.services;


import com.imageupload.example.vo.MessageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class ChatService {
    
    @Autowired
    private SimpMessagingTemplate simpMessageTemplate;

    public void sendNotification(MessageVo message){
        simpMessageTemplate.convertAndSend("/notification/" + message.getTargetUser(), message);
    }

}
