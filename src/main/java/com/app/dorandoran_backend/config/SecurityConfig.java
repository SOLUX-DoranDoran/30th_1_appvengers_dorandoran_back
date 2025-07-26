package com.app.dorandoran_backend.config;

import com.app.dorandoran_backend.auth.jwt.JwtAuthenticationFilter;
import com.app.dorandoran_backend.auth.jwt.JwtTokenProvider;
import com.app.dorandoran_backend.auth.oauth.CustomOAuth2SuccessHandler;
import com.app.dorandoran_backend.auth.service.PrincipalOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;
    private final PrincipalOAuth2UserService principalOauth2UserService;
    private final CorsConfig corsConfig;
    private final CustomOAuth2SuccessHandler customOAuth2SuccessHandler;

    // AuthenticationManager Bean 등록
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .headers(headers -> headers.frameOptions(frameOptions -> frameOptions.sameOrigin()))

                // 인가 설정
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/",
                                "/favicon.ico",
                                "/api/auth/reissue",
                                "/api/auth/oauth/naver",
                                "/api/auth/oauth/google",
                                "/oauth2/authorization/google",
                                "/oauth2/authorization/naver",
                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/swagger-resources/**",
                                "/webjars/**",
                                "/h2-console/**",
                                "/error"
                        ).permitAll()
                        .anyRequest().authenticated()
                )

                // OAuth2 설정
                .oauth2Login(oauth2 -> oauth2
                        .userInfoEndpoint(userInfo -> userInfo.userService(principalOauth2UserService))
                        .successHandler(customOAuth2SuccessHandler)
                )

                // 필터 등록
                .addFilterBefore(corsConfig.corsFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}