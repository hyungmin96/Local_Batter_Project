package com.imageupload.example.services;

import java.io.IOException;
import java.security.Principal;

import com.imageupload.example.components.DeleteFile;
import com.imageupload.example.components.GenerateFile;
import com.imageupload.example.dto.UserDTO;
import com.imageupload.example.entity.NotificationEntity;
import com.imageupload.example.entity.ProfileEntity;
import com.imageupload.example.entity.UserEntity;
import com.imageupload.example.vo.Role;
import com.imageupload.example.repositories.ProfileRepository;
import com.imageupload.example.repositories.UserRepository;
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

        UserEntity userEntity = userRepository.findByUsername(user.getName()).get();

        String saveFilePath;

        if(file != null){
            saveFilePath = new GenerateFile(file).createFile().get(0).getFileName();
            new DeleteFile(new String[]{userEntity.getProfile().getProfilePath()}).deleteFile();
        } else saveFilePath = userEntity.getProfile().getProfilePath();

        profile.setId(userEntity.getProfile().getId());
        profile.setProfilePath(saveFilePath);
        profileRepository.save(profile);

        return userEntity;
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
        .profilePath("/images/default_profile_img.png")
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
