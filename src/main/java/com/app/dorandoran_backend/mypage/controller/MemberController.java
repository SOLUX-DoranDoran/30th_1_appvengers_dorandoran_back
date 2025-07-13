/*package com.app.dorandoran_backend.mypage.controller;

import com.app.dorandoran_backend.mypage.Entity.Members;
import com.app.dorandoran_backend.mypage.dto.MemberResponseDto;
import com.app.dorandoran_backend.mypage.service.*;
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
   private final MemberProfileService memberProfileService;
    private final MemberReviewService memberReviewService;
    private final MemberQuoteService memberQuoteService;

    @GetMapping("/me")
    public ResponseEntity<MemberResponseDto> getMyPageInfo() {
        Members member = memberService.getCurrentMember();
       return ResponseEntity.ok(MemberResponseDto.from(member));
    }

    @PostMapping("/me/profile_image")
    public ResponseEntity<?> uploadProfileImage(@RequestPart("image") MultipartFile imageFile) throws IOException {
        Members member = memberService.getCurrentMember();
        String imageUrl = memberProfileService.uploadProfileImage(member, imageFile);
        return ResponseEntity.ok(Map.of("profileImageUrl", imageUrl));
    }

    @GetMapping("/me/reviews")
    public ResponseEntity<?> getReviews() {
        Members member = memberService.getCurrentMember();
        var reviews = memberReviewService.findReviewsByMember(member);
        return ResponseEntity.ok(reviews);
    }

    @GetMapping("/me/quotes")
    public ResponseEntity<?> getQuotes() {
        Members member = memberService.getCurrentMember();
        var quotes = memberQuoteService.findQuotesByMember(member);
        return ResponseEntity.ok(quotes);
    }
}
*/
