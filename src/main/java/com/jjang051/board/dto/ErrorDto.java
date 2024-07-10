package com.jjang051.board.dto;

import com.jjang051.board.code.ErrorCode;
import lombok.*;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorDto {
    private ErrorCode errorCode;
    private String errorMessage;
}
