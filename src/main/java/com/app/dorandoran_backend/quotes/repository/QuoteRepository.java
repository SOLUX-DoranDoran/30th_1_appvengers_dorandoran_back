package com.app.dorandoran_backend.quotes.repository;

import com.app.dorandoran_backend.mypage.Entity.Members;
import com.app.dorandoran_backend.quotes.Entity.Quote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuoteRepository extends JpaRepository<Quote, Long> {
    List<Quote> findByMemberOrderByCreatedAtDesc(Members member);
}
