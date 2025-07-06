package com.app.dorandoran_backend.reviews.repository;

import com.app.dorandoran_backend.reviews.Entity.ReviewLike;
import com.app.dorandoran_backend.reviews.Entity.ReviewLikeId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewLikeRepository extends JpaRepository<ReviewLike, ReviewLikeId> {
    // 유저 ID와 리뷰 ID로 좋아요 존재 여부 확인
    boolean existsByMemberIdAndReviewId(Long memberId, Long reviewId);

    // 유저 ID와 리뷰 ID로 좋아요 삭제
    void deleteByMemberIdAndReviewId(Long memberId, Long reviewId);

    // 리뷰 ID로 좋아요 개수 조회
    long countByReviewId(Long reviewId);
}
