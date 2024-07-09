package com.jjang051.board.config;


import com.jjang051.board.handler.UserLoginFailHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Controller;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    //bean 등록  스프링 컨테이너의 관리하에 들어가게 하고 싶다.


    //private final UserLoginFailHandler userLoginFailHandler;

    private final AuthenticationFailureHandler customFailureHandler;


    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        //시큐리티에서 체크하는 것 제외 시키기... 정적요소 제외 시키기
        //안쓰면 403  (권한없음)  404(페이지 없음)  405(bad request)
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

    //16개 걸쳐서 controller로 전달됨....
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        //인증요청
        // 특정페이지는 시큐리티 적용을 받게하고 나머지는 풀어줘라...
        // 람다식으로만 작성해야함...
        httpSecurity.authorizeHttpRequests((auth) -> auth
                .requestMatchers(
                        "/",
                        "/member/login",
                        "/member/signin",
                        "/member/duplicate-id"

                )
                .permitAll()
                .requestMatchers("/admin","/admin/**").hasRole("ADMIN")
                .requestMatchers("/mypage/**").hasAnyRole("ADMIN","USER")
                .anyRequest().authenticated()
        );
        httpSecurity.formLogin((auth)->
                auth
                    .loginPage("/member/login")           // get
                    .loginProcessingUrl("/member/login_process")  // post
                    .usernameParameter("userId")          // username
                    .passwordParameter("password")        // password
                    .defaultSuccessUrl("/board/list",true)
                    .failureHandler(customFailureHandler)
                    .permitAll()
        );
        httpSecurity.logout((auth)->
            auth
                    .logoutRequestMatcher(new AntPathRequestMatcher("/member/logout"))
                    .logoutSuccessUrl("/")
                    .invalidateHttpSession(true)
        );
        //httpSecurity.csrf((auth)->auth.disable());

        //security를 쓰면 로그인을 내가 하는게 아니라 시큐리티가 시켜줌...

        return httpSecurity.build();
    }

}
