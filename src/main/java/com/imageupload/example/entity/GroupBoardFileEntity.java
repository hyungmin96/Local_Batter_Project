package com.imageupload.example.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class GroupBoardFileEntity {

    @Id @GeneratedValue
    private Long Id;

    private String name;
    private String path;
    private Long size;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "BoardId")
    @JsonIgnore
    private GroupBoardEntity groupBoard;

}
