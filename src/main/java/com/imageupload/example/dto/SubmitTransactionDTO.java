package com.imageupload.example.dto;

import lombok.Data;

@Data
public class SubmitTransactionDTO {
    private String type;
    private String boardId;
    private String sellerId;
    private String buyerId;
}
