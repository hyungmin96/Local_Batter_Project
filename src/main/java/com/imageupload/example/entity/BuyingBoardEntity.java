package com.imageupload.example.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BuyingBoardEntity {

    @Id @GeneratedValue
    private Long boardId;

    private Long groupId;
    private String title;
    private String content;
    private String writer;

    @OneToMany(mappedBy = "buyingBoard")
    private List<BuyingCommentEntity> comments = new ArrayList<>();

    @OneToMany(mappedBy = "buyingBoard")
    private List<BuyingBoardFileEntity> Files = new ArrayList<>();
}
