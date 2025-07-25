/*package com.app.dorandoran_backend.mypage.controller;

import com.app.dorandoran_backend.mypage.entity.Members;
import com.app.dorandoran_backend.mypage.dto.MemberResponseDto;
import com.app.dorandoran_backend.mypage.dto.StorageImgResponseDto;
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
        if (imageFile == null || imageFile.isEmpty()) {
            return ResponseEntity
                    .badRequest()
                    .body(Map.of(
                            "success", false,
                            "message", "업로드할 이미지 파일이 없습니다."
                    ));
        }

        String originalFilename = imageFile.getOriginalFilename();
        if (originalFilename == null ||
                !(originalFilename.endsWith(".jpg") || originalFilename.endsWith(".jpeg") || originalFilename.endsWith(".png"))) {
            return ResponseEntity
                    .badRequest()
                    .body(Map.of(
                            "success", false,
                            "message", "지원하지 않는 파일 형식입니다. (jpg, jpeg, png만 허용)"
                    ));
        }

        Members member = memberService.getCurrentMember();
        StorageImgResponseDto imageUrl = memberProfileService.uploadProfileImage(member, imageFile);
        return ResponseEntity.ok(imageUrl);
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
