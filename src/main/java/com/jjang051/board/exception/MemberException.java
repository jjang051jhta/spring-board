package com.jjang051.board.exception;

import lombok.Getter;

@Getter
public class MemberException extends RuntimeException {
    private String detailMessage;
    private String errorCode;


    public MemberException(String detailMessage, String errorCode) {
        this.detailMessage = detailMessage;
        this.errorCode = errorCode;
    }

    public MemberException(String detailMessage) {
        this.detailMessage = detailMessage;
    }
}
