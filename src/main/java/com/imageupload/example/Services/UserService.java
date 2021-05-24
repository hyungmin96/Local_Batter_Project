package com.imageupload.example.Services;

import com.imageupload.example.JpaRepositories.UserRepository;
import com.imageupload.example.Vo.UserVo;
import com.imageupload.example.Vo.UserPrincipalVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService{
    
    @Autowired
    private UserRepository userRepository;

    public String userSave(UserVo vo){
        vo.setPassword(new BCryptPasswordEncoder().encode(vo.getPassword()));
        userRepository.save(vo);
        return "회원가입 성공";
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{

        UserVo userVo = userRepository.findByUsername(username);

        return new UserPrincipalVo(userVo);
        
    }

}
