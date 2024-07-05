package com.jjang051.board.controller;

import com.jjang051.board.dto.*;
import com.jjang051.board.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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


//    @PostMapping("/duplicate-id")
//    @ResponseBody
//    public void duplicateId(@RequestBody MemberDto memberDto) {
//        log.info("userId==={}",memberDto.getUserId());
//        int result = memberService.duplicateId(memberDto.getUserId());
//        log.info("result==={}",result);
//    }
    @PostMapping("/duplicate-id")
    @ResponseBody
    public ResponseEntity<Object> duplicateId(@RequestParam String userId) {
        log.info("userId==={}",userId);
        int result = memberService.duplicateId(userId);
        log.info("result==={}",result);
        Map<String, String> map = new HashMap<>();
        AlertDto alertDto = null;
        ResultDto resultDto = null;
        if(result>0) {
            //map.put("isOk", "fail");
            alertDto = AlertDto.builder()
                    .text("쓸 수 없는 아이디입니다.")
                    .icon("error")
                    .title("fail").build();
            resultDto =
                    ResultDto.builder()
                            .httpStatus(HttpStatus.OK)
                            .resultData(alertDto)
                            .message("fail")
                            .build();
        } else {
            //map.put("isOk", "ok");
            alertDto = AlertDto.builder()
                    .text("쓸 수 있는 아이디입니다.")
                    .icon("success")
                    .title("ok").build();
            resultDto =
                    ResultDto.builder()
                            .httpStatus(HttpStatus.OK)
                            .resultData(alertDto)
                            .message("ok")
                            .build();
        }
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(
                new MediaType("application","json",
                        Charset.forName("utf-8")));

        //return new ResponseEntity<>(map,httpHeaders,HttpStatus.OK);
        // rest api


        return new ResponseEntity<>(resultDto,HttpStatus.OK);
        //return ResponseEntity.ok(map);
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

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("loginDto",new LoginDto());
        return "member/login";
    }
    @PostMapping("/login")
    public String loginProcess(@Valid @ModelAttribute LoginDto loginDto,
                               BindingResult bindingResult,
                               HttpServletRequest request
    ) {
        log.info("memberDto==={}",loginDto.toString());
        if(bindingResult.hasErrors()) {
            //@ModelAttribute("객체 이름 적는 곳") MemberDto memberDto에 넘어온 값을 가지고 돌아간다.
            //이때 이름을 작성하지 않았으므로 MemberDto의 첫글자를 소문자로 바꾸어서 전달한다.
            log.info("error");
            return "member/login";
        }

        MemberDto loginMemberDto = memberService.login(loginDto);
        if(loginMemberDto==null) {
            //다시 front로 넘기기
        bindingResult
                .reject("loginFail","아이디 패스워드 확인해 주세요.");
            return "member/login";
        }

        HttpSession httpSession = request.getSession();
        httpSession.setAttribute("userName",loginMemberDto.getUserName());
        httpSession.setAttribute("userId",loginMemberDto.getUserId());
        log.info("loginMemberDto==={}",loginMemberDto);
        return "redirect:/board/list";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession httpSession = request.getSession();
        httpSession.invalidate();
        return "redirect:/";
    }

    @GetMapping("/info")
    public String info(HttpServletRequest request,
                       RedirectAttributes redirectAttributes) {
        //1. 로그인 되어 있지 않을때 요청이 들어오면 login으로 보내기
        //2. 로그인 되어 있으면 db에서 정보 조회해서 model에 실어서 내려 보내주기
        HttpSession httpSession = request.getSession();
        String userName = (String)httpSession.getAttribute("userName");
        if(userName==null) {
            AlertDto alertDto = AlertDto.builder()
                    .title("LOGIN")
                    .text("로그인 먼저 하세요.").icon("warning").build();
            redirectAttributes.addFlashAttribute("alertDto", alertDto);
            return "redirect:/member/login";
        }
        //memberService.info();
        return "member/info-pass";
    }

    @PostMapping("/info")
    public String infoProcess(@ModelAttribute MemberDto memberDto,Model model) {
        //id, password
        MemberDto infoMemberDto = memberService.info(memberDto);
        model.addAttribute("infoMemberDto",infoMemberDto);
        return "member/info";
    }

}
