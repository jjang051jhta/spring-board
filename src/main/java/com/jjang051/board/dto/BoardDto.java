package com.jjang051.board.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BoardDto {
    private int id;

    @NotBlank(message = "제목은 필수입력사항입니다.")
    private String title;

    @NotBlank(message = "내용은 필수입력사항입니다.")
    @Size(min = 10, max = 300, message = "최소 10글자 최대 300글자까지 쓸 수 있습니다.")
    private String content;


    @NotBlank(message = "내용은 필수입력사항입니다.")
    @Size(min = 4, max = 20, message = "최소 4글자 최대 20글자까지 쓸 수 있습니다.")
    private String password;

    private String regDate;

    @Builder
    public BoardDto(String title, String content, String password) {
        this.title = title;
        this.content = content;
        this.password = password;
    }
}
