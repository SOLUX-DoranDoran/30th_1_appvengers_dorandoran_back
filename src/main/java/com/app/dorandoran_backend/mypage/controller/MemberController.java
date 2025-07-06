package com.app.dorandoran_backend.mypage.controller;

import com.app.dorandoran_backend.mypage.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
public class MemberController {
    private final MemberRepository memberRepository;

    @GetMapping("/me")
    public String user() {
        return "user";
    }

    @GetMapping("/me/books")
    public String userBooks() {
        return "userBooks";
    }

    @GetMapping("/me/reading-records")
    public String userReadingRecords() {
        return "userReadingRecords";
    }

    @GetMapping("/me/statistics")
    public String userStatistics() {
        return "userStatistics";
    }
}
