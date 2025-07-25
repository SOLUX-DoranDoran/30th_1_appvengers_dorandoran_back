package com.app.dorandoran_backend.reviews.service;

import com.app.dorandoran_backend.mypage.entity.Members;
import com.app.dorandoran_backend.reviews.entity.ReviewLike;
import com.app.dorandoran_backend.reviews.entity.ReviewPost;
import com.app.dorandoran_backend.reviews.repository.ReviewLikeRepository;
import com.app.dorandoran_backend.reviews.repository.ReviewPostRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewLikeService {

    private final ReviewLikeRepository reviewLikeRepository;
    private final ReviewPostRepository reviewPostRepository;

    @Transactional
    public void likeReview(Members member, ReviewPost review) {
        // 중복 체크
        if (reviewLikeRepository.existsByReviewAndMember(review, member)) {
            throw new RuntimeException("이미 좋아요한 리뷰입니다.");
        }

        ReviewLike reviewLike = new ReviewLike();
        reviewLike.setMember(member);
        reviewLike.setReview(review);

        reviewLikeRepository.save(reviewLike);

        // 리뷰의 좋아요 수 증가
        review.setLikeCount(review.getLikeCount() + 1);
        reviewPostRepository.save(review);
    }

    @Transactional
    public void unlikeReview(Members member, ReviewPost review) {
        ReviewLike reviewLike = reviewLikeRepository.findByReviewAndMember(review, member)
                .orElseThrow(() -> new RuntimeException("좋아요한 적 없는 리뷰입니다."));

        reviewLikeRepository.delete(reviewLike);

        // 리뷰의 좋아요 수 감소
        review.setLikeCount(Math.max(0, review.getLikeCount() - 1));
        reviewPostRepository.save(review);
    }

    public boolean hasLiked(Members member, ReviewPost review) {
        return reviewLikeRepository.existsByReviewAndMember(review, member);
    }
}
