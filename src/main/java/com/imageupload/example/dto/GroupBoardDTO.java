package com.imageupload.example.dto;

import lombok.Data;
import lombok.Getter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

@Data
@ToString
public class GroupBoardDTO {

    private Long groupId;
    private String writer;
    private String content;
    private MultipartFile[] board_img;

}
