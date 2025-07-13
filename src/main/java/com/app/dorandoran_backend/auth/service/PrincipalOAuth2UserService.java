package com.app.dorandoran_backend.auth.service;

import com.app.dorandoran_backend.auth.oauth.GoogleUserInfo;
import com.app.dorandoran_backend.auth.oauth.NaverUserInfo;
import com.app.dorandoran_backend.auth.oauth.OAuth2UserInfo;
import com.app.dorandoran_backend.mypage.Entity.Members;
import com.app.dorandoran_backend.mypage.Entity.Provider;
import com.app.dorandoran_backend.mypage.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

@Service
@Slf4j
public class PrincipalOAuth2UserService extends DefaultOAuth2UserService {
    @Autowired
    private MemberRepository memberRepository;

    // loadUser: 유저 프로필 받아줌
    @Override
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(oAuth2UserRequest);
        String registrationId = oAuth2UserRequest.getClientRegistration().getRegistrationId();

        OAuth2UserInfo oAuth2UserInfo = null;
        if (registrationId.equalsIgnoreCase("google")) {
            log.info("구글 로그인 요청");
            oAuth2UserInfo = new GoogleUserInfo(oAuth2User.getAttributes());
        } else if (registrationId.equalsIgnoreCase("naver")) {
            log.info("네이버 로그인 요청");
            oAuth2UserInfo = new NaverUserInfo((Map)oAuth2User.getAttributes().get("response"));
        } else {
            throw new OAuth2AuthenticationException("지원하지 않는 소셜 로그인입니다: " + registrationId);
        }

        // 공백 제거
        String nickname = oAuth2UserInfo.getName();
        if (nickname != null) {
            nickname = nickname.replaceAll("\\s+", "");
        }

        // 기존 회원 확인
        Provider provider = oAuth2UserInfo.getProvider();
        String providerId = oAuth2UserInfo.getProviderId();

        Members member = memberRepository.findByProviderAndProviderId(provider, providerId);

        if (member == null) {
            member = Members.builder()
                    .provider(provider)
                    .providerId(providerId)
                    .email(oAuth2UserInfo.getEmail())
                    .nickname(nickname)
                    .profileImage(null)
                    .createdAt(LocalDateTime.now())
                    .build();

            memberRepository.save(member);
        }

        return new PrincipalDetails(member, oAuth2User.getAttributes());

    }
}
