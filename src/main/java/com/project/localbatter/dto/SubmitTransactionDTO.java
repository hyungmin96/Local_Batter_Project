package com.project.localbatter.dto;

import com.project.localbatter.enumtype.TransactionEnumType;

import lombok.Data;

@Data
public class SubmitTransactionDTO {
    private TransactionEnumType type;
    private Long boardId;
    private String sellerId;
    private String buyerId;
}
