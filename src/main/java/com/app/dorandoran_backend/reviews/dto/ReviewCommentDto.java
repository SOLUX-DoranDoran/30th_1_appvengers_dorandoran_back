package com.app.dorandoran_backend.reviews.dto;

import com.app.dorandoran_backend.reviews.entity.ReviewComment;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ReviewCommentDto {
    private Long id;
    private String parentCommentNickname;
    private String nickname;
    private String profileImage;
    private String content;
    private LocalDateTime createdAt;

    public static ReviewCommentDto from(ReviewComment comment) {
        return ReviewCommentDto.builder()
                .id(comment.getId())
                .parentCommentNickname(comment.getReview().getMember().getNickname())
                .nickname(comment.getMember().getNickname())
                .profileImage(comment.getMember().getProfileImage())
                .content(comment.getContent())
                .createdAt(comment.getCreatedAt())
                .build();
    }
}
