package com.project.localbatter.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class GroupJoinRequestDTO {

    private Long userId;
    private Long groupId;
    private String username;
    private String result;

}
