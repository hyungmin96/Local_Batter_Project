package com.project.localbatter.dto;

import com.project.localbatter.entity.GroupBoardEntity;
import com.project.localbatter.entity.GroupCommentEntity;
import com.project.localbatter.entity.GroupUserJoinEntity;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class GroupCommentDTO {
    private Long commentId;
    private Long boardId;
    private Long userId;
    private Long groupId;
    private String regDate;
    private String comment;
    private String result;

    public GroupCommentEntity toEntity(GroupBoardEntity groupBoard, GroupUserJoinEntity groupUser){
        return GroupCommentEntity.builder()
                .comment(comment)
                .groupId(groupId)
                .groupBoard(groupBoard)
                .groupUserJoinEntity(groupUser)
                .build();
    }

}