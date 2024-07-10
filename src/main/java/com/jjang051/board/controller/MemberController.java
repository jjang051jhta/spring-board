package com.jjang051.board.controller;

import com.jjang051.board.code.ErrorCode;
import com.jjang051.board.dto.*;
import com.jjang051.board.exception.BadRequestException;
import com.jjang051.board.exception.MemberException;
import com.jjang051.board.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import java.nio.charset.Charset;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/signin")
    public String signin(Model model) {
        model.addAttribute("memberDto", new MemberDto());
        return "member/signin";
    }


    @PostMapping("/signin")
    public String signinProcess(@Valid @ModelAttribute MemberDto memberDto,
                                BindingResult bindingResult) {

        //log.info("암호화==={}",bCryptPasswordEncoder.encode(memberDto.getPassword()));

        if (bindingResult.hasErrors()) {
            //@ModelAttribute("객체 이름 적는 곳") MemberDto memberDto에 넘어온 값을 가지고 돌아간다.
            //이때 이름을 작성하지 않았으므로 MemberDto의 첫글자를 소문자로 바꾸어서 전달한다.
            return "member/signin";
        }
        log.info("memberDto==={}", memberDto.toString());
        memberDto.setPassword(memberDto.getPassword());
        memberService.signin(memberDto);
        return "redirect:/member/login";
    }

    @PostMapping("/signin-ajax")
    @ResponseBody
    public Map<String, String> signinProcessAjax(@RequestBody MemberDto memberDto) {
        log.info("memberDto==={}", memberDto.toString());
        int result = memberService.signin(memberDto);
        Map<String, String> resultMap = new HashMap<>();
        if (result > 0) {
            resultMap.put("status", "ok");
        } else {
            resultMap.put("status", "fail");
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
        log.info("userId==={}", userId);
        int result = memberService.duplicateId(userId);
        log.info("result==={}", result);
        Map<String, String> map = new HashMap<>();
        AlertDto alertDto = null;
        ResultDto resultDto = null;
        if (result > 0) {
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
                new MediaType("application", "json",
                        Charset.forName("utf-8")));

        //return new ResponseEntity<>(map,httpHeaders,HttpStatus.OK);
        // rest api


        return new ResponseEntity<>(resultDto, HttpStatus.OK);
        //return ResponseEntity.ok(map);
    }

    @PostMapping("/body01")
    @ResponseBody
    public String xwwwformurlencoded(String name) {
        log.info("name==={}", name);
        return "httpbody로 데이터 받아보기";
    }

    @PostMapping("/body02")
    @ResponseBody
    public String textplain(@RequestBody String name) {
        log.info("name==={}", name);
        return "textplain로 데이터 받아보기";
    }

    @PostMapping("/body03")
    @ResponseBody
    public String applicationjson(@RequestBody String name) {
        log.info("name==={}", name);
        return "applicationjson로 데이터 받아보기";
    }

    @PostMapping("/body04")
    @ResponseBody
    public String applicationjsonObject(@RequestBody TestDto testDto) {
        log.info("name==={}", testDto);
        //타입을 갹체로 받으면 객체를 리턴해준다.
        return "applicationjson로 데이터 받아보기";
    }


    @PostMapping("/body05")
    @ResponseBody
    public String applicationjsonObject02(@RequestBody TestDto testDto) {
        log.info("name==={}", testDto);
        return "applicationjson로 데이터 받아보기";
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("loginDto", new LoginDto());
        return "member/login";
    }

    @PostMapping("/login")
    public String loginProcess(@Valid @ModelAttribute LoginDto loginDto,
                               BindingResult bindingResult,
                               HttpServletRequest request
    ) {
        if (bindingResult.hasErrors()) {
            return "member/login";
        }
        MemberDto loginMemberDto = memberService.login(loginDto);
        if (loginMemberDto == null) {
            bindingResult
                    .reject("loginFail", "아이디 패스워드 확인해 주세요.");
            return "member/login";
        }
        return "forward:/member/login_process";
    }


    @PostMapping("/login-fail")
    public String loginfail(@Valid LoginDto loginDto, BindingResult bindingResult) {
        log.info("loginfail===");
        return "member/login";
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
        //memberService.info();
        return "member/info-pass";
    }

    @PostMapping("/info")
    public String infoProcess(@ModelAttribute MemberDto memberDto,
                              @AuthenticationPrincipal UserDetails userDetails,
                              Model model) {
        //id, password
        //패스워드비교
        log.info("password==={}", memberDto.getPassword());  //1234
        log.info("userDetails.getPassword()==={}", userDetails.getPassword());
        log.info("userDetails.getUsername()==={}", userDetails.getUsername());


        if (bCryptPasswordEncoder.matches(memberDto.getPassword(),
                userDetails.getPassword())) {
            MemberDto infoMemberDto = memberService.info(memberDto);
            log.info("infoMemberDto==={}", infoMemberDto.toString());
            model.addAttribute("infoMemberDto", infoMemberDto);
            return "member/info";
        }
        //throw new RuntimeException("오류입니다.");
        throw new BadRequestException(ErrorCode.BAD_REQUEST);
    }

    @GetMapping("/delete")
    public String delete() {
        return "member/delete-pass";
    }

    @PostMapping("/delete")
    public String deleteProcess(@ModelAttribute MemberDto memberDto,
                                @AuthenticationPrincipal UserDetails userDetails,
                                Model model) {

        if (bCryptPasswordEncoder.matches(memberDto.getPassword(),
                userDetails.getPassword())) {
            int result = memberService.deleteMember(memberDto);
            log.info("result==={}",result);
            if(result>0) {
                SecurityContextHolder.getContext().setAuthentication(null);
                return "redirect:/";
            }
        }
        throw new BadRequestException(ErrorCode.BAD_REQUEST);
    }
}
