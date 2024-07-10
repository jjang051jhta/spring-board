package com.jjang051.board.exception;

import com.jjang051.board.dto.ErrorDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@ControllerAdvice
@Slf4j
public class CustomExceptionHandler {

    @ExceptionHandler({RuntimeException.class, NullPointerException.class})
    public String runtimeHandling() {
        return "error/5xx";
    }

    @ExceptionHandler(MemberException.class)
    @ResponseBody
    public ErrorDto memberExceptionHandling(MemberException e, Model model) {

        ErrorDto errorDto =
                ErrorDto.builder()
                .errorCode(e.getErrorCode())
                .errorMessage(e.getErrorCode().getMessage())
                .build();
        //model.addAttribute("errorDto",errorDto);
        return errorDto;
    }

    @ExceptionHandler(BadRequestException.class)
    //@ResponseBody
    public String badRequestExceptionHandling(BadRequestException e, Model model) {
        ErrorDto errorDto =
                ErrorDto.builder()
                        .errorCode(e.getErrorCode())
                        .errorMessage(e.getErrorCode().getMessage())
                        .build();

        model.addAttribute("errorDto",errorDto);
        return "member/info-pass";
    }



}
