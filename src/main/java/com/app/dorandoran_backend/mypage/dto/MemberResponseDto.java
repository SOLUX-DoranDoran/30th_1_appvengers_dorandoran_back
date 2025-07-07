package com.app.dorandoran_backend.mypage.dto;

import com.app.dorandoran_backend.mypage.Entity.Members;

import java.time.LocalDateTime;

public record MemberResponseDto(
        Long id,
        String email,
        String nickname,
        String profileImage,
        LocalDateTime createdAt
) {
    public static MemberResponseDto from(Members member) {
        return new MemberResponseDto(
                member.getId(),
                member.getEmail(),
                member.getNickname(),
                member.getProfileImage(),
                member.getCreatedAt()
        );
    }
}
