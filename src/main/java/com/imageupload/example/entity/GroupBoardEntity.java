package com.imageupload.example.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class GroupBoardEntity {

    @Id @GeneratedValue
    private Long boardId;

    private Long groupId;
    private String content;
    private int boardLike;

    @CreationTimestamp
    private Timestamp regDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "writer")
    private GroupUsersEntity groupUsersEntity;

    @OneToMany(mappedBy = "groupBoard", cascade = CascadeType.ALL)
    private List<GroupCommentEntity> comments;

    @OneToMany(mappedBy = "groupBoard", cascade = CascadeType.ALL)
    private List<GroupBoardFileEntity> files;
}
