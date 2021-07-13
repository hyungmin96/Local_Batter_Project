package com.imageupload.example.dto;

import com.imageupload.example.enumtype.NotificationEnumType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class NotificationDTO {
    
    private String result;
    private String sender;
    private NotificationEnumType notificationType;
    private String message;
    private Long roomId;
    private String date;
    private String target;

}
