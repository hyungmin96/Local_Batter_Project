package com.imageupload.example.dto;

import lombok.Data;

@Data
public class CommentDTO {
    
    private Long userId;
    private Long boardId;
    private Long seller;
    private String comment;
    private String writer;
    
}
