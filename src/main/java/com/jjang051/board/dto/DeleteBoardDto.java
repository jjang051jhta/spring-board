package com.jjang051.board.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeleteBoardDto {
    private int id;
    private String password;
}
