package com.project.localbatter.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Setter @Getter
public class ProfileEditDTO {

    private Long userId;
    private MultipartFile[] profileImage;
    private String username;
    private String introduce;

}
