package com.imageupload.example.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class MessageVo {
    
    private Long roomId;
    private String sender;
    private String target;
    private String message;
    private String date;

}
