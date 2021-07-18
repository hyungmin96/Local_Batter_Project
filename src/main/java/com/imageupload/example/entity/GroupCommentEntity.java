package com.imageupload.example.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GroupCommentEntity {

    @Id @GeneratedValue
    private Long commentId;

    private String comment;
    private String writer;

    @ManyToOne
    @JoinColumn(name = "boardId")
    @JsonIgnore
    private GroupBoardEntity groupBoard;

}
