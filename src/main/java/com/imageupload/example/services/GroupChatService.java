package com.imageupload.example.services;

import com.imageupload.example.dto.GroupChatMessageDTO;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;

@Service
@RequiredArgsConstructor
public class GroupChatService {
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final GroupService buyingService;

    public void sendGroupRoomToChat(GroupChatMessageDTO messageDTO){
        buyingService.saveChatEntity(messageDTO);
        simpMessagingTemplate.convertAndSend("/chat/buying/" + messageDTO.getRoomId(), messageDTO);
    }

    public void sendImageToGroupChatRoom(GroupChatMessageDTO messageDTO){
        messageDTO.setImg(null);
        for(String img : messageDTO.getImgPath()){
            messageDTO.setMessage(img);
            simpMessagingTemplate.convertAndSend("/chat/buying/" + messageDTO.getRoomId(), messageDTO);
        }
    }
}
