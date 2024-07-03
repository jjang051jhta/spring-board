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

    @Builder
    public BoardDto(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
