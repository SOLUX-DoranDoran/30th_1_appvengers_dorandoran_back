package com.app.dorandoran_backend.quotes.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.app.dorandoran_backend.mypage.Entity.Members;
import com.app.dorandoran_backend.mypage.service.MemberService;
import com.app.dorandoran_backend.quotes.Entity.QuotePost;
import com.app.dorandoran_backend.quotes.repository.QuoteRepository;
import com.app.dorandoran_backend.quotes.service.QuoteService;
import com.app.dorandoran_backend.quotes.service.QuotesLikeService;

import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class QuotesController {
    private final QuoteRepository quoteRepository;
    private final MemberService memberService;
    private final QuotesLikeService quoteLikeService;
    private final QuoteService quoteService;

    @GetMapping("/quotes/{quoteId}")
    public ResponseEntity<?> review(@PathVariable Long quoteId) {
        return ResponseEntity.ok(quoteService.getQuoteById(quoteId));
    }

    @PostMapping("/quotes/{quoteId}/like")
    public ResponseEntity<?> likeQuote(@PathVariable Long quoteId) {
    	System.out.println("likeQuote 진입: " + quoteId);
        Members member = memberService.getCurrentMember();
        QuotePost quote = quoteRepository.findById(quoteId)
                .orElseThrow(() -> new RuntimeException("명언을 찾을 수 없습니다."));

        try {
        	quoteLikeService.likeQuote(member, quote);
            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "message", "명언에 좋아요를 눌렀습니다.",
                    "likeCount",quote.getLikeCount()
            ));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "success", false,
                    "message", e.getMessage()
            ));
        }
    }

    @DeleteMapping("/quotes/{quoteId}/like")
    public ResponseEntity<?> unlikeQuote(@PathVariable Long quoteId) {
        Members member = memberService.getCurrentMember();
        QuotePost quote = quoteRepository.findById(quoteId)
                .orElseThrow(() -> new RuntimeException("명언을 찾을 수 없습니다."));

        try {
        	quoteLikeService.unlikeQuote(member, quote);
            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "message", "리뷰 좋아요가 취소되었습니다.",
                    "likeCount", quote.getLikeCount()
            ));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "success", false,
                    "message", e.getMessage()
            ));
        }
    }
  
    @GetMapping("/quotes")
    public ResponseEntity<?> getRecentQuotes(
            @RequestParam(name = "sort", required = false, defaultValue = "recent") String sort,
            @RequestParam(name = "page", required = false, defaultValue = "1") int page,
            @RequestParam(name = "size", required = false, defaultValue = "10") int size) {

        return ResponseEntity.ok(quoteService.getQuoteList(sort, page, size));
    }
}
