package com.app.dorandoran_backend.mypage.Entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@Table(name = "members")
public class Members{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 유저 고유 ID

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Provider provider; // 소셜 제공자 (GOOGLE, NAVER)

    @Column(name = "provider_id", length = 100, nullable = false)
    private String providerId; // 소셜 유저 ID

    @Column(length = 100)
    private String email; // 이메일

    @Column(length = 20, nullable = false)
    private String nickname; // 유저 닉네임

    @Column(name = "profile_image", length = 255)
    private String profileImage; // 프로필 이미지 URL

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt; // 가입 일시

}
