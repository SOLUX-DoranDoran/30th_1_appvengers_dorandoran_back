package com.app.dorandoran_backend.quotes.repository;

import com.app.dorandoran_backend.quotes.Entity.QuoteLike;
import com.app.dorandoran_backend.quotes.Entity.QuoteLikeId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuoteLikeRepository extends JpaRepository<QuoteLike, QuoteLikeId> {
    // 특정 명언에 달린 좋아요 개수 조회
    long countByQuoteId(Long quoteId);

    // 특정 사용자가 특정 명언에 누른 좋아요 삭제
    void deleteByMemberIdAndQuoteId(Long memberId, Long quoteId);
}
