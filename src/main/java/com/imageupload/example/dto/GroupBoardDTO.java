package com.imageupload.example.dto;

import com.imageupload.example.entity.GroupBoardEntity;
import com.imageupload.example.entity.GroupBoardFileEntity;
import com.imageupload.example.entity.GroupUsersEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class GroupBoardDTO {

    private Long groupId;
    private String content;
    private GroupUsersEntity user;
    private MultipartFile[] board_img;
    private List<GroupBoardFileEntity> files = new ArrayList<>();

    public GroupBoardEntity toEntity(){
        return GroupBoardEntity.builder()
                .groupId(groupId)
                .content(content)
                .boardLike(0)
                .groupUsersEntity(user)
                .files(files)
                .build();

    }

}
