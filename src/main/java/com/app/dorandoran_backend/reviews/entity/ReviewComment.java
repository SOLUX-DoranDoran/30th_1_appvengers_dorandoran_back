package com.app.dorandoran_backend.reviews.entity;

import com.app.dorandoran_backend.mypage.entity.Members;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "comments")
@Getter
@Setter
@NoArgsConstructor
public class ReviewComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 대댓글 ID

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id", nullable = false)
    private ReviewPost review; // 리뷰 글 ID

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private Members member; // 대댓글 작성자 ID

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content; // 대댓글 내용

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt; // 작성 일시

    @Builder
    public ReviewComment(ReviewPost review, Members member, String content, LocalDateTime createdAt) {
        this.review = review;
        this.member = member;
        this.content = content;
        this.createdAt = createdAt;
    }

}

