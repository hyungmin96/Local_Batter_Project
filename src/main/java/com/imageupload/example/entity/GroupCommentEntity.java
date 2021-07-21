package com.imageupload.example.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class GroupCommentEntity{

    @Id @GeneratedValue
    private Long commentId;

    private String comment;

    @CreationTimestamp
    private Timestamp regDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "writer")
    private GroupUsersEntity writer;

    @ManyToOne
    @JoinColumn(name = "boardId")
    @JsonIgnore
    private GroupBoardEntity groupBoard;

}
