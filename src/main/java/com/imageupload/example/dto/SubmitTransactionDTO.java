package com.imageupload.example.dto;

import com.imageupload.example.enumtype.TransactionEnumType;

import lombok.Data;

@Data
public class SubmitTransactionDTO {
    private TransactionEnumType type;
    private Long boardId;
    private String sellerId;
    private String buyerId;
}
