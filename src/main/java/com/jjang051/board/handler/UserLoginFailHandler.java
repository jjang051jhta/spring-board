package com.jjang051.board.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URLEncoder;

@Component
public class UserLoginFailHandler extends SimpleUrlAuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String errorMsg="";
        if(exception instanceof BadCredentialsException) {
            errorMsg = "아이디 패스워드 확인해 주세요";
        } else {
            errorMsg = "알 수 없는 이유로 로그인에 실패했습니다. 관리자에게 문의해주세요.";
        }
        errorMsg = URLEncoder.encode(errorMsg,"UTF-8");
        setDefaultFailureUrl("/member/login-fail?error=true&exception="+errorMsg);
        super.onAuthenticationFailure(request,response,exception);
    }
}
