package com.project.localbatter.dto;

import com.project.localbatter.entity.GroupChatEntity;
import com.project.localbatter.entity.GroupEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter @Setter
public class GroupChatMessageDTO implements Serializable {

    private int page;
    private int display;

    private Long groupId;
    private Long userId;
    private String message;
    private String username;
    private String profile;
    private String type;
    private MultipartFile[] img;
    private LocalDateTime regTime = LocalDateTime.now();

    public GroupChatEntity toEntity(GroupEntity groupEntity){
        return GroupChatEntity.builder()
                .user(userId)
                .message(message)
                .groupEntity(groupEntity)
                .type(type)
                .build();
    }
}