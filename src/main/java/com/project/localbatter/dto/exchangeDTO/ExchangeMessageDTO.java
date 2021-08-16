package com.project.localbatter.dto.exchangeDTO;

import com.project.localbatter.entity.ExchangeChatEntity;
import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class ExchangeMessageDTO {

    private Long writerId;
    private Long clientId;
    private String message;

    public ExchangeChatEntity toEntity(){
        return ExchangeChatEntity.builder()
                .writerId(writerId)
                .clientId(clientId)
                .message(message)
                .build();
    }

}
