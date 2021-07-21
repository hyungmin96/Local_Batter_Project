package com.imageupload.example.dto;

import com.imageupload.example.entity.GroupBoardEntity;
import com.imageupload.example.entity.GroupBoardFileEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter @Setter
public class GroupBoardDTO {

    private Long groupId;
    private String content;
    private String user_id;
    private String profile_img;
    private String user_name;
    private MultipartFile[] board_img;
    private List<GroupBoardFileEntity> files;

    public GroupBoardEntity toEntity(){
        return GroupBoardEntity.builder()
                .groupId(groupId)
                .content(content)
                .boardLike(0)
                .files(files)
                .build();

    }

}
