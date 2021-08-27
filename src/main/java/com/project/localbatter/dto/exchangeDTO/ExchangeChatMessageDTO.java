package com.project.localbatter.dto.exchangeDTO;

import com.project.localbatter.entity.ExchangeChatEntity;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter @Getter
public class ExchangeChatMessageDTO {

    private Long senderId;
    private String senderUsername;
    private String senderProfile;

    private Long receiveId;
    private String receiveUsername;
    private String receiveProfile;

    private Long exchangeId;
    private Long messageId;
    private ExchangeMessageType type;
    private String coordinate;
    private String message;
    private LocalDateTime regTime = LocalDateTime.now();

    public enum ExchangeMessageType{
        enter, quit, image, text, location
    }

    public ExchangeChatEntity toEntity(){
        return ExchangeChatEntity
                .builder()
                .senderId(senderId)
                .receiveId(receiveId)
                .message(message)
                .coordinate(coordinate)
                .type(type)
                .exchangeId(exchangeId)
                .build();
    }
}
