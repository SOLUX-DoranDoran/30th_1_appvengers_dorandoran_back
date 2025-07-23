package com.app.dorandoran_backend.quotes.repository;

import com.app.dorandoran_backend.mypage.Entity.Members;
//import com.app.dorandoran_backend.quotes.Entity.Quote;
import com.app.dorandoran_backend.quotes.Entity.QuotePost;
import com.app.dorandoran_backend.reviews.Entity.ReviewPost;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuoteRepository extends JpaRepository<QuotePost, Long> {
    List<QuotePost> findByMemberOrderByCreatedAtDesc(Members member);
    Page<QuotePost> findAll(Pageable pageable);
    List<QuotePost> findTop10ByOrderByCreatedAtDesc();
}
