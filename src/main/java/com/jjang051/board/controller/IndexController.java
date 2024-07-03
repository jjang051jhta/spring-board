package com.jjang051.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

// Bean으로 등록될려면 annotation을 붙이면 된다.
// @Component, @Controller, @Service 등을 붙이면
// spring의 scan(component scan) 대상이 되고
// 싱글턴으로 관리함 즉 전부 같은 객체가 됨
// 우린 이걸 가져다 쓰기만 하면 됨
@Controller
public class IndexController {

    @GetMapping({"/index","/welcome","/"})
    public String index() {
        return "index/index";
    }

}
