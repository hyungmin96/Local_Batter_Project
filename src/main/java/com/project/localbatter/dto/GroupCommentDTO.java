package com.project.localbatter.dto;

import com.project.localbatter.entity.GroupBoardEntity;
import com.project.localbatter.entity.GroupCommentEntity;
import com.project.localbatter.entity.GroupUsersEntity;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class GroupCommentDTO {
    private Long commentId;
    private Long boardId;
    private Long userId;
    private Long groupId;
    private String regDate;
    private GroupUsersEntity writer;
    private String comment;
    private GroupBoardEntity groupBoard;
    private String result;

    public GroupCommentEntity toEntity(){
        return GroupCommentEntity.builder()
                .comment(comment)
                .writer(writer)
                .groupId(groupId)
                .groupBoard(groupBoard)
                .build();
    }

}
