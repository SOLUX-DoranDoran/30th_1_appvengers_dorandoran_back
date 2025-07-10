package com.app.dorandoran_backend.mypage.service;

import com.app.dorandoran_backend.mypage.Entity.Members;
import com.app.dorandoran_backend.mypage.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    // 현재 인증된 사용자(멤버) 정보 조회
    // 인증 정보가 없거나 멤버를 못 찾으면 RuntimeException 발생
    public Members getCurrentMember() {
        Authentication authentication = org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("인증 정보가 없습니다.");
        }

        String providerId = authentication.getName();

        return memberRepository.findByProviderId(providerId)
                .orElseThrow(() -> new RuntimeException("해당 유저를 찾을 수 없습니다."));
    }
}
