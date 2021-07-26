package com.imageupload.example.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class GroupCommentEntity extends BaseTimeEntity{

    @Id @GeneratedValue
    private Long commentId;

    private Long groupId;
    private String comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "writer")
    private GroupUsersEntity writer;

    @ManyToOne
    @JoinColumn(name = "boardId")
    @JsonIgnoreProperties({"comments", "files", "groupId"})
    private GroupBoardEntity groupBoard;

    public void update(String comment){
        this.comment = comment;
    }

}
