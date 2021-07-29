package com.project.localbatter.entity;

import lombok.*;
import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class GroupBoardEntity extends BaseTimeEntity {

    @Id @GeneratedValue
    private Long boardId;

    private Long groupId;
    @Lob
    private String content;
    private int boardLike;

    @Enumerated(EnumType.STRING)
    private BoardType type;

    @ManyToOne(fetch = FetchType.LAZY)
    private GroupEntity group;

    @OneToMany(mappedBy = "groupBoard", cascade = CascadeType.ALL)
    private List<GroupCommentEntity> comments;

    @OneToMany(mappedBy = "groupBoard", cascade = CascadeType.ALL)
    private List<GroupBoardFileEntity> files;

    public enum BoardType{
        general, notice, fix
    }

    public void updateNotice(BoardType type){
        this.type = type;
    }

}
