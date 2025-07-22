package com.app.dorandoran_backend.quotes.Entity;

import java.time.LocalDateTime;

import com.app.dorandoran_backend.home.Entity.Books;
import com.app.dorandoran_backend.mypage.Entity.Members;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "quotes")
@Getter
@Setter
@NoArgsConstructor
public class QuotePost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 글귀 ID

    // 작성자(회원) - NOT NULL, ManyToOne
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Members member; // 유저 ID

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", nullable = false)
    private Books book; // 리뷰 대상 도서 ID

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content; // 글귀 내용

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt; // 작성 일시

    @Column(name = "like_count", nullable = false)
    private int likeCount = 0; // 좋아요 수

}

