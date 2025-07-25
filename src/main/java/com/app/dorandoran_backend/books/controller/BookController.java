package com.app.dorandoran_backend.books.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.dorandoran_backend.home.entity.Books;
import com.app.dorandoran_backend.home.repository.BookRepository;
import com.app.dorandoran_backend.mypage.entity.Members;
import com.app.dorandoran_backend.mypage.service.MemberService;
import com.app.dorandoran_backend.reviews.dto.ReviewDto;
import com.app.dorandoran_backend.reviews.service.ReviewService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class BookController {
	
	private final BookRepository bookRepository;
	private final MemberService memberService;
	private final ReviewService reviewService; 
	    
	@GetMapping("/books/{bookId}")
    public ResponseEntity<?> getBook(@PathVariable("bookId") Long bookId) {
        Books book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("해당 책을 찾을 수 없습니다."));
        return ResponseEntity.ok(book);
    }
	
	@GetMapping("/books")
    public ResponseEntity<?> getBookList() {
        List<Books> books = bookRepository.findAll();
        return ResponseEntity.ok(books);
    }
	
	@PostMapping("/books/{bookid}/reviews")
    public ResponseEntity<?> writeReview(@PathVariable("bookid") Long bookId,
                                         @Valid @RequestBody ReviewDto reviewDto) {
       
		Members member = memberService.getCurrentMember();
        reviewDto.setBookId(bookId);
        Long reviewId = reviewService.createReview(member, reviewDto);
        return ResponseEntity.ok(Map.of(
            "message", "리뷰가 성공적으로 등록되었습니다.",
            "reviewId", reviewId
        ));
    }
}
