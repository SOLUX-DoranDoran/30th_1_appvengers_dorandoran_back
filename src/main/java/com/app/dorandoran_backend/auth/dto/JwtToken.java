package com.app.dorandoran_backend.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class JwtToken {
    private String grantType; // "password" 또는 "refresh_token"
    private String accessToken; // JWT 액세스 토큰
    private String refreshToken; // JWT 리프레시 토큰
}
