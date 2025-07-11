package com.app.dorandoran_backend.auth.oauth;

import com.app.dorandoran_backend.auth.dto.JwtToken;
import com.app.dorandoran_backend.auth.jwt.JwtTokenProvider;
import com.app.dorandoran_backend.mypage.Entity.Members;
import com.app.dorandoran_backend.mypage.repository.MemberRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class CustomOAuth2SuccessHandler implements AuthenticationSuccessHandler {
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberRepository memberRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        JwtToken jwtToken = jwtTokenProvider.generateToken(authentication);

        // 사용자 정보 가져오기
        String providerId = authentication.getName();
        Members member = memberRepository.findByProviderId(providerId)
                .orElseThrow(() -> new IllegalArgumentException("회원 없음"));

        log.info("OAuth2 로그인 성공 - providerId: {}", providerId);

        // refreshToken DB에 저장
        member.setRefreshToken(jwtToken.getRefreshToken());
        memberRepository.save(member);

        // accessToken만 앱으로 리디렉션
        String redirectUri = UriComponentsBuilder
                .fromUriString("http://localhost:8080/oauth2/callback")
                // 앱 딥링크 (dorandoran-scheme://oauth2/callback)
                // 웹 테스트 (http://localhost:8080/oauth2/callback)
                .queryParam("accessToken", jwtToken.getAccessToken())
                .build()
                .toUriString();

        // 앱으로 리디렉션
        response.sendRedirect(redirectUri);
    }
}
