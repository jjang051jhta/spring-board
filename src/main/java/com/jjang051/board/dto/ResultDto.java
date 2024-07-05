package com.jjang051.board.dto;

import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ResultDto {
    private HttpStatus httpStatus;
    private String message;
    private Object resultData; //
}
