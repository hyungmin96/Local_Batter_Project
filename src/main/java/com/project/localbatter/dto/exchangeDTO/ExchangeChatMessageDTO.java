package com.project.localbatter.dto.exchangeDTO;

import com.project.localbatter.entity.Exchange.WriterClientJoinEntity;
import com.project.localbatter.entity.ExchangeChatEntity;
import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class ExchangeChatMessageDTO {

    private Long userId;

    private Long targetId;
    private String targetUsername;
    private String targetProfile;

    private Long exchangeId;
    private String message;
    private ExchangeMessageType type;

    public enum ExchangeMessageType{
        enter, quit, image, text
    }

    public ExchangeChatEntity toEntity(WriterClientJoinEntity writerClientJoinEntity){
        return ExchangeChatEntity
                .builder()
                .senderId(userId)
                .receiveId(targetId)
                .message(message)
                .type(type)
                .exchangeId(exchangeId)
                .wrtierClientJoinId(writerClientJoinEntity)
                .build();
    }

}
