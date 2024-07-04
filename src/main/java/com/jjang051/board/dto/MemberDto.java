package com.jjang051.board.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MemberDto {
    private int id;
    private String regDate;
    //Validation th:object=${memberDto} th:field=*{userId}

    @NotBlank(message = "user id는 필수입력사항입니다.")
    @Size(min = 5,max = 20, message = "5글자 이상 20글자 이하로 작성해 주세요")
    private String userId;

    @NotBlank(message = "user id는 필수입력사항입니다.")
    @Size(min = 2,max = 20, message = "2글자 이상 20글자 이하로 작성해 주세요")
    private String userName;


    @Email(message = "이메일 형식에 맞게 입력해주세요.")
    private String email;

    @NotBlank(message = "password는 필수입력사항입니다.")
//    @Pattern(regexp = "/^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*?_]).{8,16}$/",
//            message = "최소8글자 최대 16글자 영문자, 숫자, 특수문자를 하나 이상 포함해야 합니다.")
    private String password;
}
