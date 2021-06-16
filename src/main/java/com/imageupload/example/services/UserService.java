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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UserService implements UserDetailsService{
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProfileRepository profileRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        
        UserDetails userVo = userRepository.findByUsername(username).orElse(null);
        
        if(userVo == null)
            userVo = new UserEntity();

        return userVo;
    }

    public UserEntity userUpdate(Principal user, MultipartFile file, ProfileEntity profile) throws IOException{

        UserEntity userEntity = userRepository.findByUsername(user.getName()).get();

        String saveFilePath = new GeneratePorifleImage(file).generateFile();

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

}
