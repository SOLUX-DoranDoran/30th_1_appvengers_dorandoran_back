package com.app.dorandoran_backend.auth.service;

import com.app.dorandoran_backend.mypage.entity.Members;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

@Data
public class PrincipalDetails  implements UserDetails, OAuth2User {
    private Members member; // 회원 정보
    private Map<String, Object> attributes; // OAuth2 사용자 정보

    // 소셜 로그인용 생성자
    public PrincipalDetails(Members member, Map<String, Object> attributes) {
        this.member = member;
        this.attributes = attributes;
    }

    // 권한 설정
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
    }

    // OAuth2User용 메서드
    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    // UserDetails용 메서드
    @Override
    public String getUsername() {
        return member.getProviderId();
    }

    @Override
    public String getPassword() {
        return ""; // 비밀번호는 사용하지 않음
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    // OAuth2User용 메서드
    @Override
    public String getName() {
        return member.getNickname();
    }
}
