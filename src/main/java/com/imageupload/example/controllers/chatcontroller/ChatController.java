package com.imageupload.example.controllers.chatcontroller;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.imageupload.example.dto.BuyingChatMessageDTO;
import com.imageupload.example.dto.MessageDTO;
import com.imageupload.example.dto.NotificationDTO;
import com.imageupload.example.entity.NotificationEntity;
import com.imageupload.example.services.BuyingChatService;
import com.imageupload.example.services.ChatService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ChatController {
    
    private final ChatService chatService;
    private final BuyingChatService buyingChatService;

    @MessageMapping("/send/chat/buying/{id}")
    public void buyingRoomChat(BuyingChatMessageDTO messageDTO){
        buyingChatService.sendBuyingRoomToChat(messageDTO);
    }

    @MessageMapping("/send/notification/{id}")
    public void getConnectionMessage(HttpSession session,@Payload NotificationDTO message){
        chatService.sendNotification(session, message);
    }

    @GetMapping("/chatlist/{username}")
    public String getChatList(HttpSession session){
        chatService.clearNotification(session, "chat");
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
    public void sendMessage(Principal principal, MessageDTO message){
        chatService.sendMessage(principal, message);
    }

    @MessageMapping("/send/chat/profile")
    public void sendProfile(Principal principal, MessageDTO message){
        chatService.sendProfile(principal, message);
    }

    @MessageMapping("/send/chat/number")
    public void sendNumber(Principal principal, MessageDTO message){
        chatService.sendNumber(principal, message);
    }

}
