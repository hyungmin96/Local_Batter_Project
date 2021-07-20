package com.imageupload.example.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GroupCommentEntity {

    @Id @GeneratedValue
    private Long commentId;

    private String comment;

    @CreationTimestamp
    private Timestamp regDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "writer_id")
    private GroupUsersEntity writer;

    @ManyToOne
    @JoinColumn(name = "boardId")
    @JsonIgnore
    private GroupBoardEntity groupBoard;

}
