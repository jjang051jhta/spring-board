package com.jjang051.board.config;


import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Controller;


@Configuration
@EnableWebSecurity
public class SecurityConfig {
    //bean 등록  스프링 컨테이너의 관리하에 들어가게 하고 싶다.

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web -> web.ignoring()
                .requestMatchers(
                        "/css/**",
                        "/js/**",
                        "/images/**",
                        "/h2-console/**",
                        "/mapper/**"
                )
        );
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        //인증요청
        // 특정페이지는 시큐리티 적용을 받게하고 나머지는 풀어줘라...
        // 람다식으로만 작성해야함...
        httpSecurity.authorizeHttpRequests((auth) -> auth
                .requestMatchers(
                        "/",
                        "/member/login",
                        "/member/signin")
                .permitAll()
                .anyRequest().authenticated()
        );
        httpSecurity.formLogin((auth)->
                auth
                        .loginPage("/member/login")           // get
                        .loginProcessingUrl("/member/login")  // post
                        .usernameParameter("userId")          // username
                        .passwordParameter("password")        // password
                        .permitAll()
        );

        //security를 쓰면 로그인을 내가 하는게 아니라 시큐리티가 시켜줌...

        return httpSecurity.build();
    }

}
