package com.app.dorandoran_backend.auth.controller;

import com.app.dorandoran_backend.auth.dto.JwtToken;
import com.app.dorandoran_backend.auth.jwt.JwtTokenProvider;
import com.app.dorandoran_backend.auth.service.PrincipalDetails;
import com.app.dorandoran_backend.mypage.Entity.Members;
import com.app.dorandoran_backend.mypage.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor // 생성자 주입 자동 생성
public class AuthController {
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberRepository memberRepository;

    // 테스트용 API
    @PostMapping("/test")
    public String test() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        String nickname = principalDetails.getMember().getNickname();
        return nickname + "님, 안녕하세요!";
    }

    // 리프레시 토큰 불러오기
    @GetMapping("/refresh-token")
    public ResponseEntity<?> getRefreshToken(Authentication authentication) {
        String providerId = authentication.getName();
        Members member = memberRepository.findByProviderId(providerId)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저를 찾을 수 없습니다."));

        String refreshToken = member.getRefreshToken();
        if (refreshToken == null) {
            Map<String, Object> body = new HashMap<>();
            body.put("success", false);
            body.put("message", "리프레시 토큰이 없습니다. 다시 로그인 해주세요.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(body);
        }

        Map<String, Object> body = new HashMap<>();
        body.put("success", true);
        body.put("refreshToken", refreshToken);
        return ResponseEntity.ok(body);
    }

    // 액세스 토큰 재발급
    @PostMapping("/reissue")
    public ResponseEntity<?> reissue(@RequestBody JwtToken request) {
        try {
            String refreshToken = request.getRefreshToken();
            JwtToken newTokens = jwtTokenProvider.reissueAccessToken(refreshToken);
            return ResponseEntity.ok(newTokens);
        } catch (RuntimeException e) {
            Map<String, Object> body = new HashMap<>();
            body.put("success", false);
            body.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(body);
        }
    }

}
