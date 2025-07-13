package com.app.dorandoran_backend.auth.controller;

import com.app.dorandoran_backend.auth.dto.JwtToken;
import com.app.dorandoran_backend.auth.service.JwtTokenService;
import com.app.dorandoran_backend.auth.service.MemberAuthService;
import com.app.dorandoran_backend.auth.service.PrincipalDetails;
import com.app.dorandoran_backend.mypage.Entity.Members;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor // 생성자 주입 자동 생성
public class AuthController {
    private final JwtTokenService jwtTokenService;
    private final MemberAuthService memberAuthService;

    @GetMapping("/test")
    public String gettest() {
        String sb = "<html><body style='font-family:sans-serif;'>" +
                "<hr>" +
                "<h3 style='color:orange;'>앱 리디렉션 URL로 변경 필요</h3>" +
                "</body></html>";

        return sb;
    }

    @PostMapping("/test")
    public String test() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        String nickname = principalDetails.getMember().getNickname();
        return nickname + "님, 안녕하세요!";
    }

    @GetMapping("/refresh-token")
    public ResponseEntity<?> getRefreshToken(Authentication authentication) {
        String providerId = authentication.getName();
        Members member = memberAuthService.findByProviderId(providerId);

        String refreshToken = member.getRefreshToken();
        if (refreshToken == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of(
                            "success", false,
                            "message", "리프레시 토큰이 없습니다. 다시 로그인 해주세요."
                    ));
        }

        return ResponseEntity.ok(Map.of(
                "success", true,
                "refreshToken", refreshToken
        ));
    }

    @PostMapping("/reissue")
    public ResponseEntity<?> reissue(@RequestBody JwtToken request) {
        try {
            JwtToken newTokens = jwtTokenService.reissueAccessToken(request.getRefreshToken());
            return ResponseEntity.ok(newTokens);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of(
                            "success", false,
                            "message", e.getMessage()
                    ));
        }
    }

}
