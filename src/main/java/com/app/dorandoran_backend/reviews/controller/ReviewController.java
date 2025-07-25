package com.app.dorandoran_backend.reviews.controller;

import com.app.dorandoran_backend.reviews.dto.ReviewCommentDto;
import com.app.dorandoran_backend.reviews.dto.ReviewCommentRequestDto;
import com.app.dorandoran_backend.reviews.dto.ReviewDto;
import com.app.dorandoran_backend.reviews.entity.ReviewPost;
import com.app.dorandoran_backend.mypage.entity.Members;
import com.app.dorandoran_backend.mypage.service.MemberService;
import com.app.dorandoran_backend.reviews.repository.ReviewPostRepository;
import com.app.dorandoran_backend.reviews.service.ReviewLikeService;
import com.app.dorandoran_backend.reviews.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewPostRepository reviewPostRepository;
    private final MemberService memberService;
    private final ReviewLikeService reviewLikeService;
    private final ReviewService reviewService;

    @GetMapping("/reviews/{reviewId}")
    public ResponseEntity<?> review(@PathVariable Long reviewId) {
        return ResponseEntity.ok(reviewService.getReviewById(reviewId));
    }
    
    @PostMapping("/reviews/{reviewId}/like")
    public ResponseEntity<?> likeReview(@PathVariable Long reviewId) {
        Members member = memberService.getCurrentMember();
        ReviewPost review = reviewPostRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("리뷰를 찾을 수 없습니다."));

        try {
            reviewLikeService.likeReview(member, review);
            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "message", "리뷰에 좋아요를 눌렀습니다.",
                    "likeCount", review.getLikeCount()
            ));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "success", false,
                    "message", e.getMessage()
            ));
        }
    }
    
    @GetMapping("/books/{bookId}/reviews")
    public ResponseEntity<?> getReviewsByBook(
            @PathVariable Long bookId,
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "size", defaultValue = "10") int size) {
        
        // 페이징된 ReviewDto 리스트 반환
        var reviewPage = reviewService.getReviewsByBookId(bookId, page, size);

        return ResponseEntity.ok(Map.of(
                "reviews", reviewPage.getContent(),
                "currentPage", reviewPage.getNumber() + 1,  // 0-based 인덱스 보정
                "totalPages", reviewPage.getTotalPages(),
                "totalReviews", reviewPage.getTotalElements()
        ));
    }

    @DeleteMapping("/reviews/{reviewId}/like")
    public ResponseEntity<?> unlikeReview(@PathVariable Long reviewId) {
        Members member = memberService.getCurrentMember();
        ReviewPost review = reviewPostRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("리뷰를 찾을 수 없습니다."));

        try {
            reviewLikeService.unlikeReview(member, review);
            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "message", "리뷰 좋아요가 취소되었습니다.",
                    "likeCount", review.getLikeCount()
            ));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "success", false,
                    "message", e.getMessage()
            ));
        }
    }

    @GetMapping("/reviews/{reviewId}/comments")
    public ResponseEntity<?> reviewComments(
            @PathVariable Long reviewId,
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "size", defaultValue = "10") int size) {

        Page<ReviewCommentDto> commentPage = reviewService.getComments(reviewId, page, size);

        return ResponseEntity.ok(Map.of(
                "comments", commentPage.getContent(),
                "currentPage", commentPage.getNumber() + 1,
                "totalPages", commentPage.getTotalPages(),
                "totalComments", commentPage.getTotalElements()
        ));
    }

    @PostMapping("/reviews/{reviewId}/comments")
    public ResponseEntity<?> createReviewComment(@PathVariable Long reviewId,
                                                 @Valid @RequestBody ReviewCommentRequestDto reviewCommentRequestDto) {

        Members member = memberService.getCurrentMember();
        Long commentId = reviewService.createComment(member, reviewId, reviewCommentRequestDto.getContent());

        return ResponseEntity.ok(Map.of(
                "message", "댓글이 성공적으로 작성되었습니다.",
                "commentId", commentId
        ));

    }


    @GetMapping("/reviews")
    public ResponseEntity<?> getRecentReviews(
            @RequestParam(name = "sort", required = false, defaultValue = "recent") String sort,
            @RequestParam(name = "page", required = false, defaultValue = "1") int page,
            @RequestParam(name = "size", required = false, defaultValue = "10") int size) {

        return ResponseEntity.ok(reviewService.getReviewList(sort, page, size));
    }

}
