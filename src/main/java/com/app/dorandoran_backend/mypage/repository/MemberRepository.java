package com.app.dorandoran_backend.mypage.repository;

import com.app.dorandoran_backend.mypage.entity.Members;
import com.app.dorandoran_backend.mypage.entity.Provider;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Members, Long> {

    // 이메일로 회원 조회
    Optional<Members> findByEmail(String email);

    // 소셜 제공자와 ID로 회원 조회
    Members findByProviderAndProviderId(Provider provider, String providerId);

    // 소셜 ID로 회원 조회
    Optional<Members> findByProviderId(String providerId);

    // 회원 닉네임으로 조회
    boolean existsByNickname(String nickname);

}
