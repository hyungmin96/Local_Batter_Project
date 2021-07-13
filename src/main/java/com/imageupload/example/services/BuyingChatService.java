package com.imageupload.example.services;

import com.imageupload.example.dto.BuyingChatMessageDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class BuyingChatService {

    private final SimpMessagingTemplate simpMessagingTemplate;
    private final BuyingService buyingService;

    public void sendBuyingRoomToChat(BuyingChatMessageDTO messageDTO){
        buyingService.saveChatEntity(messageDTO);
        simpMessagingTemplate.convertAndSend("/chat/buying/" + messageDTO.getRoomId(), messageDTO);
    }

    public void sendImageToBuyingChatRoom(BuyingChatMessageDTO messageDTO){
        messageDTO.setImg(null);
        for(String img : messageDTO.getImgPath()){
            messageDTO.setMessage(img);
            simpMessagingTemplate.convertAndSend("/chat/buying/" + messageDTO.getRoomId(), messageDTO);
        }
    }
}
