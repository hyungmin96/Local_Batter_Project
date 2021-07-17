package com.imageupload.example.entity;


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
public class BuyingCommentEntity {

    @Id @GeneratedValue
    private Long commentId;

    private String comment;
    private String writer;

    @ManyToOne
    @JoinColumn(name = "boardId")
    private BuyingBoardEntity buyingBoard;

}
