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
public class BuyingBoardFileEntity {

    @Id @GeneratedValue
    private Long Id;

    private String name;
    private String path;
    private Long size;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "BoardId")
    private BuyingBoardEntity buyingBoard;

}
