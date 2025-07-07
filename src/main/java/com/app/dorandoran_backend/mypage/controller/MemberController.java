package com.app.dorandoran_backend.mypage.controller;

import com.app.dorandoran_backend.mypage.Entity.Members;
import com.app.dorandoran_backend.mypage.dto.MemberResponseDto;
import com.app.dorandoran_backend.mypage.repository.MemberRepository;
import com.app.dorandoran_backend.mypage.service.CloudinaryService;
import com.app.dorandoran_backend.mypage.service.MemberService;
import com.app.dorandoran_backend.quotes.dto.QuoteDto;
import com.app.dorandoran_backend.quotes.repository.QuoteRepository;
import com.app.dorandoran_backend.reviews.dto.ReviewDto;
import com.app.dorandoran_backend.reviews.repository.ReviewPostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final MemberRepository memberRepository;
    private final CloudinaryService cloudinaryService;
    private final ReviewPostRepository reviewPostRepository;
    private final QuoteRepository quoteRepository;

    // 마이페이지 정보 조회
    @GetMapping("/me")
    public ResponseEntity<MemberResponseDto> getMyPageInfo() {
        Members member = memberService.getCurrentMember();
        return ResponseEntity.ok(MemberResponseDto.from(member));
    }

    @PostMapping("/me/profile_image")
    public ResponseEntity<?> uploadProfileImage(@RequestPart("image") MultipartFile imageFile) throws IOException {
        Members member = memberService.getCurrentMember();

        // Cloudinary에 이미지 업로드
        String imageUrl = cloudinaryService.uploadImage(imageFile);

        // DB에 URL 저장
        member.setProfileImage(imageUrl);
        memberRepository.save(member);

        return ResponseEntity.ok(Map.of("profileImageUrl", imageUrl));
    }

    // 본인 리뷰 목록 조회
    @GetMapping("/me/reviews")
    public ResponseEntity<?> getReviews() {
        Members member = memberService.getCurrentMember();

        var reviews = reviewPostRepository.findByMemberOrderByCreatedAtDesc(member)
                .stream()
                .map(ReviewDto::from)
                .toList();
        return ResponseEntity.ok(reviews);
    }

    // 본인 글귀 목록 조회
    @GetMapping("/me/quotes")
    public ResponseEntity<?> getQuotes() {
        Members member = memberService.getCurrentMember();

        var quotes = quoteRepository.findByMemberOrderByCreatedAtDesc(member)
                .stream()
                .map(QuoteDto::from)
                .toList();

        return ResponseEntity.ok(quotes);
    }
}
