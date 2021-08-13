package com.project.localbatter.dto.exchangeDTO;

import com.project.localbatter.entity.Exchange.ClientExchangeEntity;
import com.project.localbatter.entity.Exchange.WriterClientJoinEntity;
import com.project.localbatter.entity.Exchange.WriterExchangeEntity;
import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class WriterClientJoinDTO {

    private WriterExchangeEntity writerExchangeEntity;
    private ClientExchangeEntity clientExchangeEntity;
    private WriterClientJoinEntity.status status = WriterClientJoinEntity.status.wait;
    private Long writerId;
    private Long clientId;

    public WriterClientJoinDTO(WriterExchangeEntity writerExchangeEntity, ClientExchangeEntity clientExchangeEntity) {
        this.writerExchangeEntity = writerExchangeEntity;
        this.clientExchangeEntity = clientExchangeEntity;
        this.writerId = writerExchangeEntity.getUserId();
        this.clientId = clientExchangeEntity.getUserId();
    }

    public WriterClientJoinEntity toEntity(){
        return WriterClientJoinEntity.builder()
                .writerExchangeEntity(writerExchangeEntity)
                .clientExchangeEntity(clientExchangeEntity)
                .status(status)
                .writerId(writerId)
                .clientId(clientId)
                .build();
    }
}
