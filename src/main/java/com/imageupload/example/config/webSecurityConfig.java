package com.imageupload.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class webSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public LoginSuccessHandler LoginSuccessHandler() {
        return new LoginSuccessHandler();
    }

    @Override
    public void configure(WebSecurity web) {
        // 이미지, 뷰, js 등 접근가능한 리소스를 설정
        web.ignoring().antMatchers("/css/**", "/js/**", "/img/**", "/lib/**");
    }
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // URL, Login, Session 등 http 접근 보안관련 설정
        http.csrf().disable()
            .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/write").authenticated()
                .antMatchers("/board/article/update/**").authenticated()
                .antMatchers("/transaction/itemList").authenticated()
                .antMatchers("/chatlist/**").authenticated()
            .and()
                .formLogin()
                    .loginPage("/user/login")
                    .loginProcessingUrl("/api/login")
                    .successHandler(LoginSuccessHandler())
                    .failureForwardUrl("/login/error")
                    .permitAll()
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

