package com.imageupload.example.dto;

import com.imageupload.example.entity.GroupEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class GroupInfoDTO {

    private String title;
    private String description;
    private String roomTitle;
    private String limit;
    private int currentUsers;
    private String owner;
    private String chk_1; // 전체공개
    private String chk_2; // 일부공개
    private String chk_3; // 비공개
    private MultipartFile[] files;
    private GroupEntity.roomEnumType type;

    public GroupEntity.roomEnumType getType(){
        if(chk_1.equals("1")) return GroupEntity.roomEnumType.공개;
        else if(chk_2.equals("2")) return GroupEntity.roomEnumType.일부공개;
        else return GroupEntity.roomEnumType.비공개;
    }

    public GroupEntity toEntity(){
        return GroupEntity.builder()
                            .title(title)
                            .description(description)
                            .currentUsers(currentUsers)
                            .owner(owner)
                            .type(GroupEntity.roomEnumType.공개)
                            .build();
    }

}
