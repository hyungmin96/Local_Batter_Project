package com.imageupload.example.controllers.chatcontroller;

import com.imageupload.example.services.ChatService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {
    
    @Autowired
    private ChatService chatService;

    

}
