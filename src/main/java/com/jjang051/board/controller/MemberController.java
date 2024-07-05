package com.jjang051.board.controller;

import com.jjang051.board.dto.MemberDto;
import com.jjang051.board.dto.TestDto;
import com.jjang051.board.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;
    @GetMapping("/signin")
    public String signin(Model model) {
        model.addAttribute("memberDto",new MemberDto());
        return "member/signin";
    }

    @PostMapping("/signin")
    public String signinProcess(@Valid @ModelAttribute MemberDto memberDto,
                                BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            //@ModelAttribute("객체 이름 적는 곳") MemberDto memberDto에 넘어온 값을 가지고 돌아간다.
            //이때 이름을 작성하지 않았으므로 MemberDto의 첫글자를 소문자로 바꾸어서 전달한다.
            return "member/signin";
        }
        log.info("memberDto==={}",memberDto.toString());

        memberService.signin(memberDto);
        return "redirect:/member/login";
    }

    @PostMapping("/signin-ajax")
    @ResponseBody
    public Map<String,String> signinProcessAjax(@RequestBody MemberDto memberDto) {
        log.info("memberDto==={}",memberDto.toString());
        int result = memberService.signin(memberDto);
        Map<String,String> resultMap = new HashMap<>();
        if(result>0) {
            resultMap.put("status","ok");
        } else {
            resultMap.put("status","fail");
        }
        return resultMap;
    }


    @PostMapping("/duplicate-id")
    @ResponseBody
    public Map<String,String> duplicateId(@RequestParam String userId) {
        

    }

    @PostMapping("/body01")
    @ResponseBody
    public String xwwwformurlencoded(String name) {
        log.info("name==={}",name);
        return "httpbody로 데이터 받아보기";
    }

    @PostMapping("/body02")
    @ResponseBody
    public String textplain(@RequestBody String name) {
        log.info("name==={}",name);
        return "textplain로 데이터 받아보기";
    }

    @PostMapping("/body03")
    @ResponseBody
    public String applicationjson(@RequestBody String name) {
        log.info("name==={}",name);
        return "applicationjson로 데이터 받아보기";
    }

    @PostMapping("/body04")
    @ResponseBody
    public String applicationjsonObject(@RequestBody TestDto testDto) {
        log.info("name==={}",testDto);
        //타입을 갹체로 받으면 객체를 리턴해준다.
        return "applicationjson로 데이터 받아보기";
    }


    @PostMapping("/body05")
    @ResponseBody
    public String applicationjsonObject02(@RequestBody TestDto testDto) {
        log.info("name==={}",testDto);
        return "applicationjson로 데이터 받아보기";
    }


}
