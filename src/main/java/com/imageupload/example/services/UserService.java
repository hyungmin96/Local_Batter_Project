package com.imageupload.example.services;

import com.imageupload.example.entity.UserEntity;
import com.imageupload.example.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService implements UserDetailsService{
    
    @Autowired
    private UserRepository userRepository;

    @Transactional(readOnly = true)
    public UserEntity findUserOne(String username){
        return userRepository.findByUsername(username).get();
    }

    public void userUpdate(UserEntity vo){
        userRepository.save(vo);
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

}
