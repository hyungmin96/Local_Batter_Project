package com.imageupload.example.dto;

import com.imageupload.example.entity.GroupChatEntity;
import com.imageupload.example.entity.GroupEntity;
import com.imageupload.example.entity.GroupFileEntity;
import com.imageupload.example.entity.GroupUsersEntity;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter @Setter
public class GroupChatMessageDTO implements Serializable {

    private Long groupId;
    private Long userId;
    private GroupUsersEntity groupUsersEntity;
    private String message;
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
