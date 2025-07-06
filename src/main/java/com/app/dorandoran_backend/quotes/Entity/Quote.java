package com.app.dorandoran_backend.quotes.Entity;

import com.app.dorandoran_backend.mypage.Entity.Members;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "quote")
@Getter
@Setter
@NoArgsConstructor
public class Quote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 글귀 ID

    // 작성자(회원) - NOT NULL, ManyToOne
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Members member; // 유저 ID

    @Column(name = "book_name", nullable = false, length = 100)
    private String bookName; // 도서 제목

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content; // 글귀 내용

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt; // 작성 일시

    @Column(name = "like_count", nullable = false)
    private int likeCount = 0; // 좋아요 수

}

