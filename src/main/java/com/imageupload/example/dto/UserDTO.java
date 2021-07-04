package com.imageupload.example.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class UserDTO {
    
    private String id;
    private String username;
    private String password;
    private String chkPassword;
    private String nickname;
    private String phone;
    private String location;

}
