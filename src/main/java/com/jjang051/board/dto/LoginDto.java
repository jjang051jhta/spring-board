package com.jjang051.board.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LoginDto {
    @NotBlank(message = "user id는 필수입력사항입니다.")
    @Size(min = 5,max = 20, message = "5글자 이상 20글자 이하로 작성해 주세요")
    private String userId;

    @NotBlank(message = "password는 필수입력사항입니다.")
    private String password;
}
