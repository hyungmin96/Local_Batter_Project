package com.project.localbatter.dto.exchangeDTO;

import com.project.localbatter.entity.Exchange.ClientExchangeEntity;
import com.project.localbatter.entity.Exchange.WriterClientJoinEntity;
import com.project.localbatter.entity.Exchange.WriterExchangeEntity;
import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class WrtierClientJoinDTO {

    private WriterExchangeEntity writerExchangeEntity;
    private ClientExchangeEntity clientExchangeEntity;
    private WriterClientJoinEntity.status status = WriterClientJoinEntity.status.wait;

    public WrtierClientJoinDTO(WriterExchangeEntity writerExchangeEntity, ClientExchangeEntity clientExchangeEntity) {
        this.writerExchangeEntity = writerExchangeEntity;
        this.clientExchangeEntity = clientExchangeEntity;
    }

    public WriterClientJoinEntity toEntity(){
        return WriterClientJoinEntity.builder()
                .writerExchangeEntity(writerExchangeEntity)
                .clientExchangeEntity(clientExchangeEntity)
                .status(status)
                .build();
    }
}
