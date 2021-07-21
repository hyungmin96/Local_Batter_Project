package com.imageupload.example.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.sql.Timestamp;

@Getter @Setter
public class GroupCommentDTO {
    private Long comment_id;
    private String user_name;
    private String comment;
    private Timestamp date;
}
