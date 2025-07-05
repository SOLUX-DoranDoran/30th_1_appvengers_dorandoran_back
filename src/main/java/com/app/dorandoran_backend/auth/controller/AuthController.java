package com.app.dorandoran_backend.auth.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor // 생성자 주입 자동 생성
public class AuthController {
    // 이메일 인증 코드 저장 Map
    private final Map<String, CodeInfo> verifyCodeMap = new ConcurrentHashMap<>();
    private static class CodeInfo {
        @Getter
        private final int code;
        private final long timestamp;

        public CodeInfo(int code) {
            this.code = code;
            this.timestamp = System.currentTimeMillis();
        }

        public boolean isExpired() {
            return System.currentTimeMillis() - timestamp > 5 * 60 * 1000; // 5분 후 만료
        }
    }

    // 테스트용 API
    @PostMapping("/test")
    public String test() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName() + "님, 안녕하세요!";
    }

    @GetMapping("/validate-token")
    public String validateToken() {
        return "validateToken";
    }

    @GetMapping("/check-username")
    public String checkUsername() {
        return "checkUsername";
    }

    @PostMapping("/oauth/naver")
    public String naverLogin() {
        return "naverLogin";
    }

    @PostMapping("/oauth/google")
    public String googleLogin() {
        return "googleLogin";
    }

}
