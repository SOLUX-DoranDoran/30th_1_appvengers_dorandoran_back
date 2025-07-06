package com.app.dorandoran_backend.auth.oauth;

import com.app.dorandoran_backend.auth.dto.JwtToken;
import com.app.dorandoran_backend.auth.jwt.JwtTokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class CustomOAuth2SuccessHandler implements AuthenticationSuccessHandler {
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        JwtToken jwtToken = jwtTokenProvider.generateToken(authentication);

        // 앱으로 리디렉트할 URI 구성
        String redirectUri = UriComponentsBuilder
                .fromUriString("http://localhost:8080/oauth2/callback")
                // 앱 딥링크 (DoranDoran-scheme://oauth2/callback)
                .queryParam("accessToken", jwtToken.getAccessToken())
                .queryParam("refreshToken", jwtToken.getRefreshToken())
                .build()
                .toUriString();

        // 앱으로 리디렉션
        response.sendRedirect(redirectUri);
    }
}
