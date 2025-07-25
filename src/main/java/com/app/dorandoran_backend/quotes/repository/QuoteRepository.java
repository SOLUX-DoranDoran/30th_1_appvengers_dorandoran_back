package com.app.dorandoran_backend.quotes.repository;

import com.app.dorandoran_backend.mypage.entity.Members;
import com.app.dorandoran_backend.quotes.entity.QuotePost;
import com.app.dorandoran_backend.reviews.entity.ReviewPost;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuoteRepository extends JpaRepository<QuotePost, Long> {
    List<QuotePost> findByMemberOrderByCreatedAtDesc(Members member);
    Page<QuotePost> findAll(Pageable pageable);
    List<QuotePost> findTop10ByOrderByCreatedAtDesc();
}
