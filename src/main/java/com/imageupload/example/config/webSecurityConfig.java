package com.imageupload.example.config;

import com.imageupload.example.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class webSecurityConfig extends WebSecurityConfigurerAdapter {

    private final  String RESOURCE_ROOT = "/resources/**";

    @Autowired
    private UserService userService;

    @Override
    public void configure(WebSecurity web) {
        // 이미지, 뷰, js 등 접근가능한 리소스를 설정
        web.ignoring().antMatchers("/css/**", "/js/**", "/img/**", "/lib/**");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder());
    }
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // URL, Login, Session 등 http 접근 보안관련 설정
        http.csrf().disable()
            .authorizeRequests()
                .antMatchers("/", RESOURCE_ROOT).permitAll()
                .antMatchers("/write").authenticated()
                .antMatchers("/board/article/update/**").authenticated()
                .antMatchers("/transaction/itemList").authenticated()
                .antMatchers("/chatlist/**").authenticated()
            .and()
                .formLogin()
                    .loginPage("/user/login")
                    .loginProcessingUrl("/api/login")
                    .failureForwardUrl("/login/error")
            .and()
                .logout()
                    .logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"))
                    .logoutSuccessUrl("/")
                    .invalidateHttpSession(true)
            .and()
                .exceptionHandling().accessDeniedPage("/error");
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
