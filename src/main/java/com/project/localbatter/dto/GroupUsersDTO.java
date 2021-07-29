package com.project.localbatter.dto;

import com.project.localbatter.entity.GroupEntity;
import com.project.localbatter.entity.GroupUsersEntity;
import com.project.localbatter.entity.UserEntity;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class GroupUsersDTO {

    private UserEntity user;
    private GroupUsersEntity.GroupUsersEnumType authorization;
    private GroupEntity groupEntity;

    public GroupUsersEntity toEntity(){
        return GroupUsersEntity.builder()
                .user(user)
                .authorization(authorization)
                .groupEntity(groupEntity)
                .build();
    }
}
