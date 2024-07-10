package com.jjang051.board.exception;

import com.jjang051.board.dto.ErrorDto;
import lombok.extern.slf4j.Slf4j;
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
    public ErrorDto memberExceptionHandling(MemberException e) {
        return ErrorDto.builder()
                .errorCode(e.getErrorCode())
                .errorMessage(e.getErrorCode().getMessage())
                .build();

    }
}
