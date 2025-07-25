package com.app.dorandoran_backend.quotes.dto;

import com.app.dorandoran_backend.quotes.entity.Quote;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record QuoteDto(
        Long id,
        String bookName,
        String content,
        LocalDateTime createdAt,
        int likeCount
) {
    public static QuoteDto from(Quote quote) {
        return QuoteDto.builder()
                .id(quote.getId())
                .bookName(quote.getBookName())
                .content(quote.getContent())
                .createdAt(quote.getCreatedAt())
                .likeCount(quote.getLikeCount())
                .build();
    }
}
