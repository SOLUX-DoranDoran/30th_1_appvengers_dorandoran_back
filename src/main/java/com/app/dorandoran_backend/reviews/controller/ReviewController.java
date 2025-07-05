package com.app.dorandoran_backend.reviews.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ReviewController {
    @GetMapping("/reviews/{reviewId}")
    public String review() {
        return "review";
    }

    @PostMapping("/reviews/{reviewId}/like")
    public String likeReview() {
        return "likeReview";
    }

    @DeleteMapping("/reviews/{reviewId}/like")
    public String unlikeReview() {
        return "unlikeReview";
    }

    @GetMapping("/reviews/{reviewId}/comments")
    public String reviewComments() {
        return "reviewComments";
    }

    @PostMapping("/reviews/{reviewId}/comments")
    public String createReviewComment() {
        return "createReviewComment";
    }

    @PostMapping("/comments/{commentId}/like")
    public String likeComment() {
        return "likeComment";
    }

    @DeleteMapping("/comments/{commentId}/like")
    public String unlikeComment() {
        return "unlikeComment";
    }

    @GetMapping("/reviews")
    public String getRecentReviews(
            @RequestParam(name = "sort", required = false, defaultValue = "recent") String sort,
            @RequestParam(name = "page", required = false, defaultValue = "1") int page,
            @RequestParam(name = "size", required = false, defaultValue = "10") int size) {
        // 실제 로직: sort 기준과 limit 수량에 따라 리뷰 조회 처리
        return "최근 리뷰 " + size + "개, 정렬 기준: " + sort + ", 페이지: " + page;
    }
}
