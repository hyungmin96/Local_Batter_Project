package com.imageupload.example.Config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import lombok.AllArgsConstructor;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class webSecirityConfig extends WebSecurityConfigurerAdapter {

    private final  String RESOURCE_ROOT = "/resources/**";

    @Override
    public void configure(WebSecurity web) {
        // 이미지, 뷰, js 등 접근가능한 리소스를 설정
        web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // URL, Login, Session 등 http 접근 보안관련 설정
        http.csrf().disable()
            .authorizeRequests()
                .antMatchers("/", RESOURCE_ROOT).permitAll()
                .antMatchers("/write").authenticated()
                .antMatchers("/board/article/update/**").authenticated()
            .and()
                .formLogin()
                    .loginPage("/user/login")
                    .loginProcessingUrl("/api/login")
                    .defaultSuccessUrl("/")
            .and()
                .logout()
                    .logoutUrl("/user/logout")
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
