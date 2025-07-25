package com.app.dorandoran_backend.quotes.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

import com.app.dorandoran_backend.quotes.entity.QuotePost;

@Getter
@Builder
public class QuoteDto {
    private Long id;
    private String bookTitle;
    private String coverImageUrl;
    private String content;
    private LocalDateTime createdAt;
    private String nickname;
    private String profileImage;

    public static QuoteDto from(QuotePost quote) {
        return QuoteDto.builder()
                .id(quote.getId())
                .bookTitle(quote.getBook().getTitle())
                .coverImageUrl(quote.getBook().getCoverImageUrl())
                .content(quote.getContent())
                .createdAt(quote.getCreatedAt())
                .nickname(quote.getMember().getNickname())
                .profileImage(quote.getMember().getProfileImage())
                .build();
    }
}

