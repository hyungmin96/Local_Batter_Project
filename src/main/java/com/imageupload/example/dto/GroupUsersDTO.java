package com.imageupload.example.dto;

import com.imageupload.example.entity.GroupEntity;
import com.imageupload.example.entity.GroupUsersEntity;
import com.imageupload.example.entity.UserEntity;
import com.imageupload.example.enumtype.GroupUsersEnumType;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class GroupUsersDTO {

    private UserEntity user;
    private GroupUsersEnumType authorization;
    private GroupEntity groupEntity;

    public GroupUsersEntity toEntity(){
        return GroupUsersEntity.builder()
                .user(user)
                .authorization(authorization)
                .groupEntity(groupEntity)
                .build();
    }

}
