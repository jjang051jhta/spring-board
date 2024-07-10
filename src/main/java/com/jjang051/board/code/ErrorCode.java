package com.jjang051.board.code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ErrorCode {
    NOT_FOUND("해당하는 아이디가 없습니다."),
    INVALID_REQUEST("잘못된 요청입니다."),
    INTERNAL_SERVER_ERROR
            ("알 수 없는 오류가 발생되었습니다. 잠시후 다시 접속해 주세요"),
    BAD_REQUEST("잘못된 요청입니다."),

    INVALID_PASSWORD("비밀번호 확인해주세요.");

    private final String message;
}
