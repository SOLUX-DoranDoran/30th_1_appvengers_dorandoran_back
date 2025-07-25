package com.app.dorandoran_backend.mypage.dto;

import java.time.LocalDateTime;

import com.app.dorandoran_backend.mypage.entity.Members;

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
