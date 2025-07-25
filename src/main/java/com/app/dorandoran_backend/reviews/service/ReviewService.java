package com.app.dorandoran_backend.reviews.service;

<<<<<<< HEAD
import com.app.dorandoran_backend.home.entity.Books;
import com.app.dorandoran_backend.home.repository.BookRepository;
import com.app.dorandoran_backend.mypage.entity.Members;
=======
import com.app.dorandoran_backend.mypage.entity.Members;
import com.app.dorandoran_backend.reviews.entity.ReviewComment;
import com.app.dorandoran_backend.reviews.entity.ReviewPost;
>>>>>>> main
import com.app.dorandoran_backend.reviews.dto.ReviewCommentDto;
import com.app.dorandoran_backend.reviews.dto.ReviewDto;
import com.app.dorandoran_backend.reviews.entity.ReviewComment;
import com.app.dorandoran_backend.reviews.entity.ReviewPost;
import com.app.dorandoran_backend.reviews.repository.ReviewCommentRepository;
import com.app.dorandoran_backend.reviews.repository.ReviewPostRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewPostRepository reviewPostRepository;
    private final ReviewCommentRepository reviewCommentRepository;
    private final BookRepository bookRepository;

    public ReviewDto getReviewById(Long reviewId) {
        ReviewPost review = reviewPostRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("리뷰를 찾을 수 없습니다."));
        return ReviewDto.from(review);
    }

    public List<ReviewDto> getReviewList(String sort, int page, int size) {
        Sort sortOption = switch (sort) {
            case "rating" -> Sort.by(Sort.Direction.DESC, "rating");
            default -> Sort.by(Sort.Direction.DESC, "createdAt");
        };
        Pageable pageable = PageRequest.of(page - 1, size, sortOption);
        return reviewPostRepository.findAll(pageable)
                .stream().map(ReviewDto::from).toList();
    }

    public Page<ReviewCommentDto> getComments(Long reviewId, int page, int size) {
        ReviewPost review = reviewPostRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("리뷰를 찾을 수 없습니다."));
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        return reviewCommentRepository.findAllByReview(review, pageable)
                .map(ReviewCommentDto::from);
    }
    
    @Transactional
    public Long createReview(Members member, ReviewDto dto) {
    	
    	Books book = bookRepository.findByTitle(dto.getBookTitle())
                .orElseThrow(() -> new IllegalArgumentException("해당 책이 없습니다"));
    	
        ReviewPost review = ReviewPost.builder()
                .member(member)
                .book(book)
                .content(dto.getContent())
                .rating(dto.getRating())
                .createdAt(LocalDateTime.now())
                .commentCount(0)
                .likeCount(0)
                .build();

        reviewPostRepository.save(review);
        return review.getId();
    }

    @Transactional
    public Long createComment(Members member, Long reviewId, String content) {
        ReviewPost review = reviewPostRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("리뷰를 찾을 수 없습니다."));

        ReviewComment comment = ReviewComment.builder()
                .review(review)
                .member(member)
                .content(content)
                .createdAt(LocalDateTime.now())
                .build();
        reviewCommentRepository.save(comment);

        review.setCommentCount(review.getCommentCount() + 1);
        reviewPostRepository.save(review);

        return comment.getId();
    }
    
    public Page<ReviewDto> getReviewsByBookId(Long bookId, int page, int size) {
        Pageable pageable = PageRequest.of(page -1, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        return reviewPostRepository.findByBookId(bookId, pageable)
                .map(ReviewDto::from);
    }
}

