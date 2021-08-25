package com.project.localbatter.dto.Group;

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
    private String tag;
    private int memberCount;
    private String result;

    public GroupPageDTO(Long groupId, String groupTitle, String description, String owner, String filePath, String tag, int memberCount) {
        this.groupId = groupId;
        this.groupTitle = groupTitle;
        this.description = description;
        this.owner = owner;
        this.filePath = filePath;
        this.tag = tag;
        this.memberCount = memberCount;
    }

}
