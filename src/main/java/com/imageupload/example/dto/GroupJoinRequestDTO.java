package com.imageupload.example.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class GroupJoinRequestDTO {

    private Long userId;
    private String username;
    private Long groupId;
    private String result;

}
