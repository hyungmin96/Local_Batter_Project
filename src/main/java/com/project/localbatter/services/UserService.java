package com.project.localbatter.services;

import java.io.IOException;
import java.security.Principal;

import com.project.localbatter.components.DeleteFile;
import com.project.localbatter.components.GenerateFile;
import com.project.localbatter.dto.UserDTO;
import com.project.localbatter.entity.NotificationEntity;
import com.project.localbatter.entity.ProfileEntity;
import com.project.localbatter.entity.UserEntity;
import com.project.localbatter.repositories.ProfileRepository;
import com.project.localbatter.vo.Role;
import com.project.localbatter.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService{
    
    final private UserRepository userRepository;
    final private ProfileRepository profileRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        
        UserDetails userVo = userRepository.findByUsername(username).orElse(null);
        
        if(userVo == null)
            return new UserEntity();

        return userVo;
    }

    public UserEntity userUpdate(Principal user, MultipartFile[] file, ProfileEntity profile) throws IOException{



        return null;
    }

    public void userSave(UserDTO vo){

        ProfileEntity profile = ProfileEntity.builder()
        .preferTime("상관없음")
        .accountNumber("미설정")
        .location(vo.getLocation())
        .introduce("자기소개")
        .nickname(vo.getNickname())
        .phoneNum(vo.getPhone())
        .mannerScore(0)
        .mileage(0)
        .build();

        profileRepository.save(profile);
        
        UserEntity userEntity = new UserEntity();

        userEntity.setUsername(vo.getUsername());
        userEntity.setRole(Role.ROLE_USER);
        userEntity.setProfile(profile);
        userEntity.setPassword(new BCryptPasswordEncoder().encode(vo.getPassword()));
        userEntity.setNotification(new NotificationEntity());
        userRepository.save(userEntity);
    }

    public boolean checkUserName(String Email){
        UserEntity userVo = userRepository.findByUsername(Email).orElse(null);
        if(userVo == null)
            return true;
        else
            return false;
    }

    public UserEntity findUserOne(String nickname){
        return userRepository.findByUsername(nickname).get();
    }

    public UserEntity findById(Long id){
        return userRepository.findById(id).get();
    }

}
