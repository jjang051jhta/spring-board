package com.jjang051.board.controller;

import com.jjang051.board.dto.MemberDto;
import com.jjang051.board.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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
        memberService.signin(memberDto);
        return "redirect:/member/login";
    }
}
