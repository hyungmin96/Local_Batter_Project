package com.imageupload.example.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class MessageDTO {
    
    private Long roomId;
    private String sender;
    private String target;
    private String message;
    private String date;

}
