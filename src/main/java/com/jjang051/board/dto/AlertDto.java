package com.jjang051.board.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AlertDto {
    private String title;
    private String text;
    private String icon;
}
