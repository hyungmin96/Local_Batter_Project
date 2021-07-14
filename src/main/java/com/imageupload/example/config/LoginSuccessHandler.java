package com.imageupload.example.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.imageupload.example.entity.NotificationEntity;
import com.imageupload.example.entity.UserEntity;
import com.imageupload.example.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

public class LoginSuccessHandler implements AuthenticationSuccessHandler{

    @Autowired
    private UserRepository userRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        
         UserEntity userEntity = userRepository.findByUsername(authentication.getName()).get();
        
         HttpSession session = request.getSession();

         session.setAttribute("userId", userEntity);

        response.sendRedirect("/");
    }
}