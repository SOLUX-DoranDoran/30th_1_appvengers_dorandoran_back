package com.app.dorandoran_backend.mypage.repository;

import com.app.dorandoran_backend.mypage.Entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Integer> {
    boolean existsByUsername(String username);
    Optional<Member> findByUsername(String username);
}
