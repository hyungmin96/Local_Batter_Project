package com.project.localbatter.controllers;

import com.project.localbatter.dto.exchangeDTO.ExchangeChatMessageDTO;
import com.project.localbatter.services.Exchange.ExchangeChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ExchangeChatController {

    private final ExchangeChatService exchangeChatService;

    @MessageMapping("/userId={id}")
    public void sendMessage(ExchangeChatMessageDTO messageDTO){
        exchangeChatService.sendMessage(messageDTO);
    }

}
