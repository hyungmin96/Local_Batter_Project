package com.imageupload.example.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class GroupBoardFileEntity {

    @Id @GeneratedValue
    private Long Id;

    private String name;
    private String path;
    private Long size;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BoardId")
    @JsonIgnore
    private GroupBoardEntity groupBoard;

}
