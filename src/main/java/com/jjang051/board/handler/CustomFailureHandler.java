package com.jjang051.board.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URLEncoder;

@Component
@Slf4j
public class CustomFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        log.info("exception==={}",request.getAttribute("userId"));
        String errorMsg = "";
        if(exception instanceof BadCredentialsException) {
            errorMsg="아이디 패스워드 확인해주세요.";
        } else if(exception instanceof InternalAuthenticationServiceException) {
            errorMsg="알 수 없는 이유로 로그인에 실패했습니다. 잠시 후 다시 시도해주세요.";
        }
        errorMsg = URLEncoder.encode(errorMsg,"UTF-8");  //utf-8
        setDefaultFailureUrl("/member/login?error=true&msg="+errorMsg);
        super.onAuthenticationFailure(request, response, exception);
    }
}
