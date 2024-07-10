package com.jjang051.board.exception;

import com.jjang051.board.code.ErrorCode;
import lombok.Getter;

@Getter
public class BadRequestException extends RuntimeException {
    private ErrorCode errorCode;
    private String detailMessage;
    public BadRequestException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public BadRequestException(ErrorCode errorCode, String detailMessage) {
        this.errorCode = errorCode;
        this.detailMessage = detailMessage;
    }
}
