package com.project.localbatter.dto.Group;

import com.project.localbatter.dto.GenerateFileDTO;
import com.project.localbatter.entity.GroupBoardEntity;
import com.project.localbatter.entity.GroupBoardFileEntity;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class GroupBoardFileDTO {

    private String name;
    private String path;
    private Long groupId;
    private Long id;
    private int size;

    public GroupBoardFileDTO(GenerateFileDTO groupFileDTO) {
        this.name = groupFileDTO.getName();
        this.path = groupFileDTO.getPath();
    }

    public GroupBoardFileEntity toEntity(GroupBoardEntity board){
        return GroupBoardFileEntity.builder()
                .name(name)
                .path(path)
                .groupBoard(board)
                .groupId(board.getGroupId())
                .build();
    }
}