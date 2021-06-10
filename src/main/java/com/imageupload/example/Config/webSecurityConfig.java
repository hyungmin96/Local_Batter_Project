package com.imageupload.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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

    @Override
    public void configure(WebSecurity web) {
        // 이미지, 뷰, js 등 접근가능한 리소스를 설정
        web.ignoring().antMatchers("/css/**", "/js/**", "/img/**", "/lib/**");
        // web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
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
