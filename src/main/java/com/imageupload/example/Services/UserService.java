package com.imageupload.example.Services;

import com.imageupload.example.JpaRepositories.UserRepository;
import com.imageupload.example.Vo.UserVo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService{
    
    @Autowired
    private UserRepository userRepository;

    public String userSave(UserVo vo){
        userRepository.save(vo);
        return "회원가입 성공";
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException{

        UserVo userVo = userRepository.findByemail(email);

        if(userVo == null){
            throw new UsernameNotFoundException("");
        }

        return userVo;
        
    }

}
