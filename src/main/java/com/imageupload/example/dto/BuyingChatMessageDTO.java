package com.imageupload.example.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class BuyingChatMessageDTO {

    private Long roomId;
    private String profilePath;
    private String sender;
    private String message;
    private String type;
    private String localDate;

}
