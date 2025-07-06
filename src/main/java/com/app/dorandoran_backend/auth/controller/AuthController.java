package com.app.dorandoran_backend.auth.controller;

import com.app.dorandoran_backend.auth.dto.JwtToken;
import com.app.dorandoran_backend.auth.jwt.JwtTokenProvider;
import com.app.dorandoran_backend.auth.service.PrincipalDetails;
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
    // 테스트용 API
    @PostMapping("/test")
    public String test() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        String nickname = principalDetails.getMember().getNickname();
        return nickname + "님, 안녕하세요!";
    }

    @GetMapping("/reissue")
    public ResponseEntity<?> reissue(@RequestBody JwtToken request) {
        String refreshToken = request.getRefreshToken();

        try {
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
