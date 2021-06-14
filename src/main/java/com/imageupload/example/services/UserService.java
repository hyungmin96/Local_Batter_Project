package com.imageupload.example.services;

import java.io.IOException;
import java.security.Principal;
import com.imageupload.example.components.GeneratePorifleImage;
import com.imageupload.example.entity.UserEntity;
import com.imageupload.example.models.UserProfileInfo;
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

    public UserEntity userUpdate(Principal user, MultipartFile file, UserProfileInfo profile) throws IOException{

        UserEntity userEntity = userRepository.findByUsername(user.getName()).get();

        String saveFilePath = new GeneratePorifleImage(file).generateFile();

        if(userEntity != null){

            userEntity.setNickname(profile.getNickname());
            userEntity.setIntroduce(profile.getIntroduce());
            userEntity.setLocation(profile.getLocation());
            userEntity.setPreferTime(profile.getPreferTime());
            userEntity.setProfileImg(saveFilePath);
            
            userRepository.save(userEntity);
            
        }

        return userEntity;
    }

    public String userSave(UserEntity vo){
        vo.setPassword(new BCryptPasswordEncoder().encode(vo.getPassword()));
        userRepository.save(vo);
        return "회원가입 성공";
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        UserDetails userVo = userRepository.findByUsername(username).orElse(null);
        if(userVo == null)
            userVo = new UserEntity();
        return userVo;
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
