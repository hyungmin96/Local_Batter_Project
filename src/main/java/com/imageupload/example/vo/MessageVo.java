package com.imageupload.example.vo;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class MessageVo {
    
    private String result;
    private String sendUser;
    private String notificationType;
    private String message;
    private LocalDateTime date;
    private String targetUser;

}
