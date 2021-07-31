package com.project.localbatter.dto;

import com.project.localbatter.entity.GroupEntity;
import com.project.localbatter.entity.GroupFileEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@NoArgsConstructor
public class GroupFileDTO {

    private String path;
    private String name;
    private GroupEntity groupEntity;
    private Long group;
    private List<String> img;

    public GroupFileDTO(GenerateFileDTO generateFileDTO) {
        this.name = generateFileDTO.getName();
        this.path = generateFileDTO.getPath();
    }

    public GroupFileEntity toEntity(GroupEntity groupEntity){
        return GroupFileEntity.builder()
                .name(name)
                .path(path)
                .group(groupEntity)
                .build();
    }

}
