package com.app.dorandoran_backend.boards.entity;

import com.app.dorandoran_backend.home.Entity.Books;
import com.app.dorandoran_backend.mypage.Entity.Members;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "boards")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // 토론방 아이디

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")  // 도서 아이디
    private Books book;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")  // 유저 고유 아이디
    private Members member;

    @Column(nullable = false)
    private String title;   // 토론 주제명

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content; // 의견

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;  // 생성 시각

}
