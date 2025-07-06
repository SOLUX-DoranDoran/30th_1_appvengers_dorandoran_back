package com.app.dorandoran_backend.debate.Entity;

import com.app.dorandoran_backend.mypage.Entity.Members;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "debateMessage")
@Getter
@Setter
@NoArgsConstructor
public class DebateMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 토론글 ID

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Members member; // 유저 ID

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", nullable = false)
    private DebateRoom room; // 토론방 ID

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content; // 메시지 내용

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt; // 작성 시각
}
