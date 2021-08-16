package com.project.localbatter.services;

import com.project.localbatter.api.exchange.ExchangeChatApiController.ResponseChatListDTO;
import com.project.localbatter.components.ExchangeQueryComponent;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExchangeChatService {

    private final SimpMessagingTemplate simpMessagingTemplate;
    private final ExchangeQueryComponent exchangeQueryComponent;

    public List<ResponseChatListDTO> getChatList(Long userId){
        return exchangeQueryComponent.getChatList(userId);
    }

}
