package com.app.dorandoran_backend.quotes;

import com.app.dorandoran_backend.mypage.Entity.Members;
import com.app.dorandoran_backend.mypage.repository.MemberRepository;
import com.app.dorandoran_backend.quotes.Entity.Quote;
import com.app.dorandoran_backend.quotes.repository.QuoteRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
public class DummyQuoteInsertTest {
    @Autowired
    private QuoteRepository quoteRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void insertDummyQuotes() {
        // 1. ID가 2번인 사용자 조회
        Members member2 = memberRepository.findById(2L)
                .orElseThrow(() -> new RuntimeException("ID가 2인 회원이 존재하지 않습니다."));


        // 2. 더미 글귀 생성
        List<Quote> quotes = List.of(
                createQuote(member2, "연을 쫓는 아이", "너를 위해서라면 천 번이라도."),
                createQuote(member2, "죽음에 관하여", "죽음이 두려운 것이 아니라, 살아 있는 동안 아무것도 하지 않는 것이 두렵다."),
                createQuote(member2, "달까지 가자", "네가 옳다는 걸 세상이 알게 하자.")
        );

        // 3. 저장
        quoteRepository.saveAll(quotes);

        System.out.println("더미 글귀 3개가 성공적으로 저장되었습니다.");
    }

    private Quote createQuote(Members member, String bookName, String content) {
        Quote quote = new Quote();
        quote.setMember(member);
        quote.setBookName(bookName);
        quote.setContent(content);
        quote.setCreatedAt(LocalDateTime.now());
        quote.setLikeCount(0);
        return quote;
    }
}
