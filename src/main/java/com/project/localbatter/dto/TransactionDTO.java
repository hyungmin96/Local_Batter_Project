package com.project.localbatter.dto;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class TransactionDTO {

    private int page;
    private int display;
    private Long userId; // user id
    private Long clientWriterExchangeId;

}
