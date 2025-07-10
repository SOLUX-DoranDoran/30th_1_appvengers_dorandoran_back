package com.app.dorandoran_backend.auth.service;

import com.app.dorandoran_backend.auth.dto.JwtToken;
import com.app.dorandoran_backend.auth.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtTokenService {
    private final JwtTokenProvider jwtTokenProvider;

    public JwtToken reissueAccessToken(String refreshToken) {
        return jwtTokenProvider.reissueAccessToken(refreshToken);
    }
}
