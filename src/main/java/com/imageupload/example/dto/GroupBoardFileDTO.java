package com.imageupload.example.dto;

import com.imageupload.example.entity.GroupBoardEntity;
import com.imageupload.example.entity.GroupBoardFileEntity;
import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class GroupBoardFileDTO {
    private String name;
    private String path;
    private GroupBoardEntity boardId;
    private int size;

    public GroupBoardFileEntity toEntity(){
        return GroupBoardFileEntity.builder()
                .name(name)
                .path(path)
                .groupBoard(boardId)
                .build();
    }

}
