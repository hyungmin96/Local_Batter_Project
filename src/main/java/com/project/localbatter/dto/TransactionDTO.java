package com.project.localbatter.dto;

import com.project.localbatter.entity.Exchange.WriterExchangeEntity;
import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class TransactionDTO {

    private int page;
    private int display;
    private Long userId; // user id
    private Long clientWriterExchangeId;
    private WriterExchangeEntity.exchangeStatus status;

}
