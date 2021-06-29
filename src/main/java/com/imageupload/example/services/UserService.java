package com.imageupload.example.services;

import java.io.IOException;
import java.security.Principal;
import com.imageupload.example.components.GeneratePorifleImage;
import com.imageupload.example.entity.ProfileEntity;
import com.imageupload.example.entity.UserEntity;
import com.imageupload.example.vo.Role;
import com.imageupload.example.repositories.ProfileRepository;
import com.imageupload.example.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
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

    public UserEntity userUpdate(Principal user, MultipartFile file, ProfileEntity profile) throws IOException{

        UserEntity userEntity = userRepository.findByUsername(user.getName()).get();

        String saveFilePath = (file != null) ? new GeneratePorifleImage(file).generateFile() : userEntity.getProfile().getProfilePath();
        
        if(userEntity != null){
            profile.setId(userEntity.getProfile().getId());
            profile.setProfilePath(saveFilePath);
            profileRepository.save(profile);
        }

        return userEntity;
    }

    public String userSave(UserEntity vo){

        ProfileEntity profile = ProfileEntity.builder()
        .preferTime("상관없음")
        .accountNumber("미설정")
        .location("미설정")
        .introduce("자기소개")
        .nickname(vo.getNickname())
        .phoneNum("미설정")
        .mannerScore(0)
        .mileage(0)
        .profilePath("/images/default_profile_img.png")
        .build();

        profileRepository.save(profile);
        
        vo.setRole(Role.ROLE_USER);
        vo.setProfile(profile);
        vo.setPassword(new BCryptPasswordEncoder().encode(vo.getPassword()));
        userRepository.save(vo);
        return "회원가입 성공";
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
