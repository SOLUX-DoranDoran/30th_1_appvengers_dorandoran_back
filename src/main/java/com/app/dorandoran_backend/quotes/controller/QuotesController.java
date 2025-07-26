package com.app.dorandoran_backend.quotes.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.app.dorandoran_backend.mypage.entity.Members;
import com.app.dorandoran_backend.mypage.service.MemberService;
import com.app.dorandoran_backend.quotes.dto.QuoteDto;
import com.app.dorandoran_backend.quotes.entity.QuotePost;
import com.app.dorandoran_backend.quotes.repository.QuoteRepository;
import com.app.dorandoran_backend.quotes.service.QuoteService;
import com.app.dorandoran_backend.quotes.service.QuotesLikeService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor

public class QuotesController {
    private final QuoteRepository quoteRepository;
    private final MemberService memberService;
    private final QuotesLikeService quoteLikeService;
    private final QuoteService quoteService;

    @GetMapping("/quots/{quoteId}")
    public ResponseEntity<?> review(@PathVariable("quoteId") Long quoteId) {
        return ResponseEntity.ok(quoteService.getQuoteById(quoteId));
    }
    
    @GetMapping("/quotes/recent")
    public ResponseEntity<List<QuoteDto>> getRecentQuotes() {
        return ResponseEntity.ok(quoteService.getRecentQuotes());
    }
    @PostMapping("/quotes/{quoteId}/like")
    public ResponseEntity<?> likeQuote(@PathVariable("quoteId") Long quoteId) {
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
    public ResponseEntity<?> unlikeQuote(@PathVariable("quoteId") Long quoteId) {
        Members member = memberService.getCurrentMember();
        QuotePost quote = quoteRepository.findById(quoteId)
                .orElseThrow(() -> new RuntimeException("명언을 찾을 수 없습니다."));

        try {
        	quoteLikeService.unlikeQuote(member, quote);
            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "message", "명언 좋아요가 취소되었습니다.",
                    "likeCount", quote.getLikeCount()
            ));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "success", false,
                    "message", e.getMessage()
            ));
        }
    }
    
    @PostMapping("/quotes")
    public ResponseEntity<?> createQuote(@Valid @RequestBody QuoteDto quoteDto) {
        Members member = memberService.getCurrentMember();  // 로그인된 회원 정보 조회
        Long quoteId = quoteService.createQuote(member, quoteDto);
        return ResponseEntity.ok(Map.of(
            "message", "명언이 성공적으로 등록되었습니다.",
            "quoteId", quoteId
        ));
    }
    @GetMapping("/quotes")
    public ResponseEntity<?> getRecentQuotes(
            @RequestParam(name = "sort", required = false, defaultValue = "recent") String sort,
            @RequestParam(name = "page", required = false, defaultValue = "1") int page,
            @RequestParam(name = "size", required = false, defaultValue = "10") int size) {

        return ResponseEntity.ok(quoteService.getQuoteList(sort, page, size));
    }
}
