package com.project.localbatter.dto;

import com.project.localbatter.entity.GroupEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;

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
    private int memberCount;
    private String result;
    private List<String> img_name = new ArrayList<>();

    public GroupPageDTO(GroupEntity groupEntity) {
        this.userId = groupEntity.getId();
        this.groupTitle = groupEntity.getGroupTitle();
        this.description = groupEntity.getDescription();
        this.owner = groupEntity.getOwner();
        this.memberCount = groupEntity.getMemberCount();
        groupEntity.getFiles().forEach(item ->img_name.add(item.getName()));
    }

}
