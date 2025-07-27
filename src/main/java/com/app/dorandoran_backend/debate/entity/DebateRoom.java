package com.app.dorandoran_backend.debate.entity;

import com.app.dorandoran_backend.home.entity.Books;
import com.app.dorandoran_backend.mypage.entity.Members;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "debateRoom")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DebateRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 토론방 ID

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", nullable = false)
    private Books book; // 도서 ID

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Members member; // 유저 ID

    @Column(nullable = false, length = 100)
    private String title; // 토론 주제명

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content; // 의견

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt; // 생성 시각
}
