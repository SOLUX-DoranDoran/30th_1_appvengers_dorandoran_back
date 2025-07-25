package com.app.dorandoran_backend.reviews.repository;

import com.app.dorandoran_backend.mypage.entity.Members;
import com.app.dorandoran_backend.reviews.entity.ReviewPost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewPostRepository extends JpaRepository<ReviewPost, Long> {
    List<ReviewPost> findByMemberOrderByCreatedAtDesc(Members member);
    Page<ReviewPost> findAll(Pageable pageable);
    List<ReviewPost> findByMember(Members member);
}
