package com.project.localbatter.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.project.localbatter.entity.UserEntity;
import com.project.localbatter.repositories.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class LoginSuccessHandler implements AuthenticationSuccessHandler{

    @Autowired
    private UserRepository userRepository;

    private final static Logger log = LogManager.getLogger();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
         UserEntity userEntity = userRepository.findByUsername(authentication.getName()).get();
         HttpSession session = request.getSession();
         session.setAttribute("g_user", userEntity);
        response.sendRedirect("/");
    }
}