package com.imageupload.example.services;

import com.imageupload.example.dto.BuyingChatMessageDTO;
import com.imageupload.example.entity.BuyingChatRoomEntity;
import com.imageupload.example.repositories.BuyingChatRepository;
import com.imageupload.example.repositories.BuyingChatRoomRepository;
import com.imageupload.example.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BuyingChatService {

    private final SimpMessagingTemplate simpMessagingTemplate;

    public void sendBuyingRoomToChat(BuyingChatMessageDTO messageDTO){
        simpMessagingTemplate.convertAndSend("/chat/buying/" + messageDTO.getRoomId(), messageDTO);
    }

}
