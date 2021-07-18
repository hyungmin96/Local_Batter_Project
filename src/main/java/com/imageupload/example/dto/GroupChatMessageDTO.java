package com.imageupload.example.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class GroupChatMessageDTO {

    private Long roomId;
    private Long userId;
    private String profilePath;
    private String sender;
    private String message;
    private String type;
    private String localDate;
    private MultipartFile[] img;
    private List<String> imgPath;

}
