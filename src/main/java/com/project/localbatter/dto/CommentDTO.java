package com.project.localbatter.dto;

import lombok.Data;

@Data
public class CommentDTO {
    
    private Long userId;
    private Long boardId;
    private Long seller;
    private String comment;
    private float mannerScore;
    private String writer;
    
}
