package com.app.dorandoran_backend.reviews.repository;

import com.app.dorandoran_backend.mypage.entity.Members;
import com.app.dorandoran_backend.reviews.entity.ReviewLike;
import com.app.dorandoran_backend.reviews.entity.ReviewLikeId;
import com.app.dorandoran_backend.reviews.entity.ReviewPost;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReviewLikeRepository extends JpaRepository<ReviewLike, ReviewLikeId> {
    // 유저와 리뷰로 좋아요 존재 여부 확인
    boolean existsByReviewAndMember(ReviewPost review, Members member);

    // 유저 ID와 리뷰 ID로 좋아요 삭제
    void deleteByMemberIdAndReviewId(Long memberId, Long reviewId);

    // 리뷰와 유저로 좋아요 삭제
    void deleteByReviewAndMember(ReviewPost review, Members member);

    // 리뷰 ID로 좋아요 개수 조회
    long countByReviewId(Long reviewId);

    // 리뷰와 유저로 좋아요 조회
    Optional<ReviewLike> findByReviewAndMember(ReviewPost review, Members member);
}
