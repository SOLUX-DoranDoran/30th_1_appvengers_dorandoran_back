package com.app.dorandoran_backend.quotes.repository;

import com.app.dorandoran_backend.mypage.entity.Members;
import com.app.dorandoran_backend.quotes.entity.QuoteLike;
import com.app.dorandoran_backend.quotes.entity.QuoteLikeId;
import com.app.dorandoran_backend.quotes.entity.QuotePost;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuoteLikeRepository extends JpaRepository<QuoteLike, QuoteLikeId> {
	// 유저와 글귀로 좋아요 존재 여부 확인
    boolean existsByQuoteAndMember(QuotePost quote, Members member);

    // 특정 명언에 달린 좋아요 개수 조회
    long countByQuoteId(Long quoteId);

    // 특정 사용자가 특정 명언에 누른 좋아요 삭제
    void deleteByMemberIdAndQuoteId(Long memberId, Long quoteId);
 // 리뷰와 유저로 좋아요 조회
    Optional<QuoteLike> findByQuoteAndMember(QuotePost quote, Members member);
}
