package com.app.dorandoran_backend.auth.jwt;

import com.app.dorandoran_backend.auth.dto.JwtToken;
import com.app.dorandoran_backend.auth.service.PrincipalDetails;
import com.app.dorandoran_backend.mypage.entity.Members;
import com.app.dorandoran_backend.mypage.repository.MemberRepository;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenProvider {
    private final MemberRepository memberRepository;
    @Value("${jwt.secret}") // 필드에 주입
    private String secretKey;

    private Key key;

    @PostConstruct // 필드 주입 이후 실행됨
    public void init() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey.trim());
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    // Member 정보를 가지고 AccessToken, RefreshToken 생성하는 메소드
    public JwtToken generateToken(Authentication authentication) {
        // 권한 가져오기
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining((",")));

        log.info("생성 중인 토큰 권한: {}", authorities);

        long now = (new Date()).getTime();

        // AccessToken 생성
        Date accessTokenExpiresIn = new Date(now + 1000 * 60 * 15); // 15분 유효
        String accessToken = Jwts.builder()
                .setSubject(authentication.getName()) // 사용자 이름
                .claim("auth", authorities) // 권한 정보
                .setExpiration(accessTokenExpiresIn) // 만료 시간 설정
                .signWith(key, SignatureAlgorithm.HS256) // 서명 알고리즘
                .compact();

        // RefreshToken 생성
        String refreshToken = Jwts.builder()
                .setSubject(authentication.getName())  // subject 추가 (providerId)
                .setExpiration(new Date(now + 1000 * 60 * 60 * 24 * 7)) // 7일 유효
                .signWith(key, SignatureAlgorithm.HS256) // 서명 알고리즘
                .compact();

        return JwtToken.builder()
                .grantType("Bearer") // JWT 토큰 타입
                .accessToken(accessToken) // 생성된 AccessToken
                .refreshToken(refreshToken) // 생성된 RefreshToken
                .build();
    }

    // JWT 토큰을 복호화하여 토큰에 들어있는 정보를 꺼내는 메서드
    public Authentication getAuthentication(String accessToken) {
        // Jwt 토큰 복호화
        Claims claims = parseClaims(accessToken);

        if(claims.get("auth") == null) {
            throw new RuntimeException("권한 정보가 없는 토큰입니다.");
        }

        // 클레임에서 권한 정보 가져오기
        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get("auth").toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        // 토큰의 providerId로 회원 정보 조회
        String providerId = claims.getSubject();
        Members member = memberRepository.findByProviderId(providerId)
                .orElseThrow(() -> new RuntimeException("해당 사용자가 존재하지 않습니다."));

        // PrincipalDetails로 래핑
        PrincipalDetails principalDetails = new PrincipalDetails(member, claims);

        return new UsernamePasswordAuthenticationToken(principalDetails, "", authorities);
    }

    // 토큰 정보를 검증하는 메서드
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key) // 서명 키 설정
                    .build()
                    .parseClaimsJws(token); // 토큰 파싱
            return true; // 토큰이 유효하면 true 반환
        } catch (SecurityException | MalformedJwtException e) {
            log.info("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            log.info("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            log.info("지원하지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            log.info("JWT 토큰이 잘못되었습니다.");
        }
        return false; // 예외 발생 시 false 반환
    }

    // accessToken에서 클레임을 파싱하는 메서드
    private Claims parseClaims(String accessToken) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key) // 서명 키 설정
                    .build()
                    .parseClaimsJws(accessToken) // 토큰 파싱
                    .getBody(); // 클레임 반환
        } catch (ExpiredJwtException e) {
            return e.getClaims(); // 만료된 토큰의 경우 클레임 반환
        }
    }

    public JwtToken reissueAccessToken(String refreshToken) {
        // 1. refreshToken 유효성 검사
        if (!validateToken(refreshToken)) {
            throw new RuntimeException("유효하지 않은 Refresh Token입니다.");
        }

        // 2. refreshToken에서 사용자 정보 추출
        Claims claims = parseClaims(refreshToken);
        String providerId = claims.getSubject();

        // 3. DB에서 회원 조회
        Members member = memberRepository.findByProviderId(providerId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        // 4. DB에 저장된 refreshToken과 비교
        if (member.getRefreshToken() == null || !member.getRefreshToken().equals(refreshToken)) {
            throw new RuntimeException("리프레시 토큰이 일치하지 않습니다.");
        }

        // 5. 새로운 AccessToken 생성
        long now = (new Date()).getTime();
        Date accessTokenExpiresIn = new Date(now + 1000 * 60 * 15); // 15분 유효
        String authorities = "ROLE_USER";

        String newAccessToken = Jwts.builder()
                .setSubject(providerId)
                .claim("auth", authorities)
                .setExpiration(accessTokenExpiresIn)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        // 6. 새로운 RefreshToken도 생성 (기존 토큰 무효화)
        Date refreshTokenExpiresIn = new Date(now + 1000 * 60 * 60 * 24 * 7); // 7일 유효
        String newRefreshToken = Jwts.builder()
                .setSubject(providerId)
                .setExpiration(refreshTokenExpiresIn)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        // 7. DB에 새로운 RefreshToken 저장
        member.setRefreshToken(newRefreshToken);
        memberRepository.save(member);

        // 8. 새 토큰 세트 반환
        return JwtToken.builder()
                .grantType("Bearer")
                .accessToken(newAccessToken)
                .refreshToken(newRefreshToken)
                .build();
    }

}
