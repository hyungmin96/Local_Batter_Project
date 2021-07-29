package com.project.localbatter.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class GroupBoardFileEntity extends BaseTimeEntity {

    @Id @GeneratedValue
    private Long id;

    private String name;
    private String path;
    private Long size;
    private Long groupId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BoardId")
    @JsonIgnore
    private GroupBoardEntity groupBoard;

}
