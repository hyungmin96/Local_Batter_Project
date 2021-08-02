package com.project.localbatter.dto;

import com.project.localbatter.entity.GroupEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter @Setter
public class GroupCreateDTO {

    private Long groupId;
    private Long userId;
    private String title;
    private String description;
    private String owner;
    private String chk_1; // 전체공개
    private String chk_2; // 일부공개
    private String chk_3; // 비공개
    private String tags;
    private GroupEntity.roomEnumType type;
    private MultipartFile[] files;
    private String filePath;

    public GroupEntity.roomEnumType getType(){
        if(chk_1.equals("1")) return GroupEntity.roomEnumType.공개;
        else if(chk_2.equals("2")) return GroupEntity.roomEnumType.일부공개;
        else return GroupEntity.roomEnumType.비공개;
    }

    public GroupEntity toEntity(){
        return GroupEntity.builder()
                .groupTitle(title)
                .description(description)
                .owner(owner)
                .filePath(filePath)
                .type(getType())
                .build();
    }

}
