package com.imageupload.example.dto;

import com.imageupload.example.entity.GroupEntity;
import com.imageupload.example.entity.GroupUsersEntity;
import com.imageupload.example.enumtype.GroupUsersEnumType;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class GroupUserInfoDTO {
    private Long userId;
    private String user_name;
    private String profilePath;
    private GroupUsersEnumType authorization;
    private GroupEntity groupEntity;

    public GroupUsersEntity toEntity(){
        return GroupUsersEntity.builder()
                .user_name(user_name)
                .profilePath(profilePath)
                .authorization(authorization)
                .groupEntity(groupEntity)
                .build();
    }

}
