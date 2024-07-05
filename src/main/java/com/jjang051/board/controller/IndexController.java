package com.jjang051.board.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

// Bean으로 등록될려면 annotation을 붙이면 된다.
// @Component, @Controller, @Service 등을 붙이면
// spring의 scan(component scan) 대상이 되고
// 싱글턴으로 관리함 즉 전부 같은 객체가 됨
// 우린 이걸 가져다 쓰기만 하면 됨
@Controller
@Slf4j
public class IndexController {

    @GetMapping({"/index","/welcome","/"})
    public String index(HttpServletRequest request,
        @SessionAttribute(required = false) String userName) {
        HttpSession httpSession = request.getSession();
        //String userName = (String) httpSession.getAttribute("userName");
        //log.info("userName=={}",userName);
        log.info("sessionUserName=={}",userName);

        return "index/index";
    }

}
