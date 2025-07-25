package com.app.dorandoran_backend.debate.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DebateCreateDto {
    private Long bookId;  // 도서 아이디

    @NotBlank(message = "토론 주제를 입력해주세요.")
    private String title; // 토론 주제명

    @NotBlank(message = "의견 내용을 입력해주세요.")
    private String content; // 의견
}
