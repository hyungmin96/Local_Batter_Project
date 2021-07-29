package com.project.localbatter.dto;

import com.project.localbatter.entity.GroupBoardEntity;
import com.project.localbatter.entity.GroupBoardFileEntity;

public class GroupBoardFileDTO {

    private String name;
    private String path;
    private GroupBoardEntity boardId;
    private Long groupId;
    private int size;

    public GroupBoardFileEntity toEntity(){
        return GroupBoardFileEntity.builder()
                .name(name)
                .path(path)
                .groupBoard(boardId)
                .groupId(groupId)
                .build();
    }

}