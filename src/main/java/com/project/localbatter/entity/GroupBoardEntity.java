package com.project.localbatter.entity;

import com.project.localbatter.dto.Group.GroupBoardDTO;
import com.project.localbatter.entity.Exchange.WriterExchangeEntity;
import lombok.*;

import javax.persistence.*;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "tbl_group_board")
public class GroupBoardEntity extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "board_id")
    private Long boardId;

    @Column(name = "group_id")
    private Long groupId;

    @Column(name = "board_title")
    private String title;

    @Lob
    @Column(name = "board_content")
    private String content;

    @Column(name = "board_like")
    private int boardLike;

    @Column(name = "board_type")
    @Enumerated(EnumType.STRING)
    private BoardType type;

    @Column(name = "board_category")
    @Enumerated(EnumType.STRING)
    private BoardCategory BoardCategory;

    @Column(name = "board_thumbnail")
    private String thumnbnailPath;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "exchange_id")
    private WriterExchangeEntity writerExchangeEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_writer")
    private GroupUserJoinEntity groupUserJoinEntity;

    @OneToMany(mappedBy = "groupBoard", fetch = LAZY, cascade=  CascadeType.ALL)
    private List<GroupCommentEntity> comments;

    @OneToMany(mappedBy = "groupBoard", fetch = LAZY, cascade = CascadeType.ALL)
    private List<GroupBoardFileEntity> files;

    public void update(GroupBoardDTO groupBoardDTO, List<GroupBoardFileEntity> files){
        this.title = groupBoardDTO.getTitle();
        this.content = groupBoardDTO.getContent();
        writerExchangeEntity.update(groupBoardDTO);
        if(files != null && files.size() > 0){
            this.files = files;
            this.thumnbnailPath = files.get(0).getName();
        }
    }

    public void updateNotice(BoardType type){
        this.type = type;
    }

    public enum BoardType{
        general, notice, fix
    }

    public enum BoardCategory{
        article, exchange, complete
    }

}
