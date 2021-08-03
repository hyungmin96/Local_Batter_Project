package com.project.localbatter.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "tbl_group_comment")
public class GroupCommentEntity extends BaseTimeEntity{

    @Id @GeneratedValue
    @Column(name = "comment_id")
    private Long commentId;

    @Column(name = "comment_groupid")
    private Long groupId;

    @Column(name = "comment_content")
    private String comment;

    @ManyToOne
    @JoinColumn(name = "comment_writer")
    private GroupUserJoinEntity groupUserJoinEntity;

    @ManyToOne
    @JoinColumn(name = "boardId")
    private GroupBoardEntity groupBoard;

    public void update(String comment){
        this.comment = comment;
    }

}
