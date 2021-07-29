package com.project.localbatter.dto;

import com.project.localbatter.entity.GroupBoardEntity;
import com.project.localbatter.entity.GroupBoardFileEntity;
import com.project.localbatter.entity.GroupUsersEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter @Setter
public class GroupBoardDTO implements Serializable {

    private Long groupId;
    private Long boardId;

    private int page;
    private int display;
    private String type = "general";
    private String username;
    private String content;
    private int boardLike = 0;
    private GroupUsersEntity user;
    private String result;

    private Timestamp regTime = new Timestamp(new Date().getTime());
    private MultipartFile[] board_img;
    private List<GroupBoardFileEntity> files = new ArrayList<>();
    private GroupCommentDTO[] comments = new GroupCommentDTO[0];

    public GroupBoardEntity.BoardType getType(){
        if(this.type.equals("notice"))
            return GroupBoardEntity.BoardType.notice;
        else if(this.type.equals("fixed"))
            return GroupBoardEntity.BoardType.fix;
        else
            return GroupBoardEntity.BoardType.general;
    }

    public GroupBoardEntity toEntity(){
        return GroupBoardEntity.builder()
                .groupId(groupId)
                .content(content)
                .boardLike(0)
                .type(getType())
                .files(files)
                .build();
    }
}
