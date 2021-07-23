package com.imageupload.example.dto;

import com.imageupload.example.entity.GroupBoardEntity;
import com.imageupload.example.entity.GroupCommentEntity;
import com.imageupload.example.entity.GroupUsersEntity;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class GroupCommentDTO {
    private Long comment_id;
    private Long boardId;
    private String regDate;
    private GroupUsersEntity writer;
    private String comment;
    private GroupBoardEntity groupBoard;

    public GroupCommentEntity toEntity(){
        return GroupCommentEntity.builder()
                .comment(comment)
                .writer(writer)
                .groupBoard(groupBoard)
                .build();
    }

}
