package com.app.dorandoran_backend.quotes;

import com.app.dorandoran_backend.mypage.Entity.Members;
import com.app.dorandoran_backend.mypage.Entity.Provider;
import com.app.dorandoran_backend.mypage.repository.MemberRepository;
import com.app.dorandoran_backend.quotes.Entity.Quote;
import com.app.dorandoran_backend.quotes.repository.QuoteRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
public class DummyQuoteInsertTest {
    @Autowired
    private QuoteRepository quoteRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void insertDummyQuotes() {
        // 1. 테스트용 사용자 조회 또는 생성
        Members member = memberRepository.findByEmail("test@example.com")
                .orElseGet(() -> {
                    Members newMember = new Members();
                    newMember.setEmail("test@example.com");
                    newMember.setNickname("테스트유저");
                    newMember.setProvider(Provider.GOOGLE);
                    newMember.setProviderId("testuser123");
                    newMember.setCreatedAt(LocalDateTime.now());
                    return memberRepository.save(newMember);
                });

        // 2. 더미 글귀 생성
        List<Quote> quotes = List.of(
                createQuote(member, "데미안", "새는 알에서 나오려고 투쟁한다. 알은 세계이다."),
                createQuote(member, "아몬드", "사람은 감정을 알아야 한다. 그래야 타인을 이해할 수 있다."),
                createQuote(member, "나미야 잡화점의 기적", "편지를 통해 세상이 조금씩 나아진다면 그걸로 충분하다.")
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
