package com.project.localbatter.controllers;

import java.security.Principal;
import javax.servlet.http.HttpSession;
import com.project.localbatter.dto.GroupChatMessageDTO;
import com.project.localbatter.dto.MessageDTO;
import com.project.localbatter.dto.NotificationDTO;
import com.project.localbatter.services.ChatService;
import com.project.localbatter.services.GroupChatService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ChatController {
    
    private final ChatService chatService;
    private final GroupChatService groupChatService;
    private final HttpSession session;

    @MessageMapping("/send/chat/group/{id}")
    public void GroupRoomChat(GroupChatMessageDTO messageDTO){
        groupChatService.sendGroupRoomToChat(messageDTO);
    }

    @MessageMapping("/send/notification/{id}")
    public void getConnectionMessage(HttpSession session,@Payload NotificationDTO message){
        chatService.sendNotification(session, message);
    }

    @GetMapping("/chatlist/{username}")
    public String getChatList(HttpSession session, @PathVariable String username){
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
