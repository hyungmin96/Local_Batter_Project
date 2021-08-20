package com.project.localbatter.dto;

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
    private MessageType type;

    public enum MessageType{
        image, text, enter, quit
    }

}
