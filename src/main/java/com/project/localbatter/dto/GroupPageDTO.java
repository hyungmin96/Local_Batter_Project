package com.project.localbatter.dto;

import com.project.localbatter.entity.GroupEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class GroupPageDTO {

    private int page;
    private int display;
    private Long userId;
    private Long groupId;
    private String groupTitle;
    private String description;
    private String owner;
    private String filePath;
    private int memberCount;
    private String result;


    public GroupPageDTO(GroupEntity groupEntity) {
        this.groupId = groupEntity.getId();
        this.groupTitle = groupEntity.getGroupTitle();
        this.description = groupEntity.getDescription();
        this.owner = groupEntity.getOwner();
        this.memberCount = groupEntity.getMemberCount();
        this.filePath = groupEntity.getFilePath();
    }

}
