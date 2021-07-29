package com.project.localbatter.dto;

import com.project.localbatter.entity.GroupChatEntity;
import com.project.localbatter.entity.GroupUsersEntity;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Getter @Setter
public class GroupChatMessageDTO implements Serializable {

    private Long groupId;
    private Long userId;
    private GroupUsersEntity groupUsersEntity;
    private String message;
    private String username;
    private String type;
    private MultipartFile[] img;
    private List<String> imgPath;
    private Timestamp regTime = new Timestamp(new Date().getTime());

    public GroupChatEntity toEntity(){
        return GroupChatEntity.builder()
                .message(message)
                .type(type)
                .groupUsersEntity(groupUsersEntity)
                .groupEntity(groupUsersEntity.getGroupEntity())
                .build();
    }
}
