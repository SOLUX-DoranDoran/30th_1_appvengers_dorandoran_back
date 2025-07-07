package com.app.dorandoran_backend.reviews.dto;

import com.app.dorandoran_backend.reviews.Entity.ReviewPost;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ReviewDto(
        Long id,
        String bookTitle,
        String coverImageUrl,
        String content,
        Byte rating,
        LocalDateTime createdAt
) {
    public static ReviewDto from(ReviewPost review) {
        return ReviewDto.builder()
                .id(review.getId())
                .bookTitle(review.getBook().getTitle())
                .coverImageUrl(review.getBook().getCoverImageUrl())
                .content(review.getContent())
                .rating(review.getRating())
                .createdAt(review.getCreatedAt())
                .build();
    }
}
