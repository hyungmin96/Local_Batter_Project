package com.project.localbatter.dto.exchangeDTO;

import com.project.localbatter.entity.ExchangeChatEntity;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter @Getter
public class ExchangeChatMessageDTO {

    private Long userId;

    private Long targetId;
    private String targetUsername;
    private String targetProfile;

    private Long exchangeId;
    private ExchangeMessageType type;
    private String message;
    private LocalDateTime regTime = LocalDateTime.now();

    public enum ExchangeMessageType{
        enter, quit, image, text
    }

    public ExchangeChatEntity toEntity(){
        return ExchangeChatEntity
                .builder()
                .senderId(userId)
                .receiveId(targetId)
                .message(message)
                .type(type)
                .exchangeId(exchangeId)
                .build();
    }

}
