package com.project.localbatter.dto;

import com.project.localbatter.entity.ProfileEntity;
import com.project.localbatter.entity.UserEntity;
import com.project.localbatter.vo.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Data
@AllArgsConstructor
@Builder
public class UserDTO {
    
    private String id;
    private String username;
    private String password;
    private String chkPassword;
    private String nickname;
    private String profilePath;
    private String phone;
    private String location;

    public UserEntity toEntity(ProfileEntity profile){
        return UserEntity.builder()
                .username(username)
                .password(new BCryptPasswordEncoder().encode(password))
                .profilePath(profilePath)
                .profile(profile)
                .Role(Role.ROLE_USER)
                .build();
    }

}
