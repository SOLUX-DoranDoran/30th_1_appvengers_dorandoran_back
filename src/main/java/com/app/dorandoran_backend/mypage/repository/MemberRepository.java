package com.app.dorandoran_backend.mypage.repository;

import com.app.dorandoran_backend.mypage.Entity.Members;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Members, Long> {

}
