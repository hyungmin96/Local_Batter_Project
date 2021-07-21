package com.imageupload.example.dto;

import com.imageupload.example.entity.GroupEntity;
import com.imageupload.example.entity.GroupFileEntity;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class GroupFileDTO {

    private String path;
    private String name;
    private GroupEntity groupEntity;

    public GroupFileEntity toEntity(){
        return GroupFileEntity.builder()
                .name(name)
                .path(path)
                .build();
    }

}
