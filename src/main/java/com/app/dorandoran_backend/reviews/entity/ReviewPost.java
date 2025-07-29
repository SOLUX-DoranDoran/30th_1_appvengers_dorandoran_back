package com.app.dorandoran_backend.reviews.entity;


import com.app.dorandoran_backend.books.entity.Books;
import com.app.dorandoran_backend.mypage.entity.Members;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "reviews")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor 
@Builder
public class ReviewPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 리뷰 글 ID

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Members member; // 리뷰 작성자 ID

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", nullable = false)
    private Books book; // 리뷰 대상 도서 ID

    @Column(columnDefinition = "TEXT")
    private String content; // 리뷰 내용

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt; // 작성 일시

    @Column(nullable = false)
    private Byte rating; // 리뷰 평점 (1~5)

    @Column(name = "like_count", nullable = false)
    private int likeCount = 0; // 좋아요 수

    @Column(name = "comment_count", nullable = false)
    private int commentCount = 0; // 대댓글 수

}
