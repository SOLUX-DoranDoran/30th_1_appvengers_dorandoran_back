package com.app.dorandoran_backend.quotes;

import com.app.dorandoran_backend.mypage.Entity.Members;
import com.app.dorandoran_backend.mypage.repository.MemberRepository;
import com.app.dorandoran_backend.quotes.Entity.Quote;
import com.app.dorandoran_backend.quotes.Entity.QuoteLike;
import com.app.dorandoran_backend.quotes.Entity.QuoteLikeId;
import com.app.dorandoran_backend.quotes.repository.QuoteLikeRepository;
import com.app.dorandoran_backend.quotes.repository.QuoteRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.Optional;

import static com.app.dorandoran_backend.mypage.Entity.Provider.GOOGLE;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback
@ActiveProfiles("test")
public class QuoteLikeTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private QuoteRepository quoteRepository;

    @Autowired
    private QuoteLikeRepository quoteLikeRepository;

    @Test
    public void testQuoteLikeSave() {
        // 1. 사용자 생성
        Members user = new Members();
        user.setNickname("테스터");
        user.setProvider(GOOGLE);
        user.setProviderId("test123");
        user.setCreatedAt(LocalDateTime.now());
        memberRepository.save(user);

        // 2. 명언 생성 (user를 작성자로 설정)
        Quote quote = new Quote();
        quote.setMember(user);
        quote.setBookName("테스트 도서");
        quote.setContent("테스트 명언 내용");
        quote.setCreatedAt(LocalDateTime.now());
        quote.setLikeCount(0);
        quoteRepository.save(quote);

        // 3. 좋아요 저장
        QuoteLike like = new QuoteLike();
        like.setMember(user);
        like.setQuote(quote);
        quoteLikeRepository.save(like);

        // 4. 검증 - 좋아요 저장됨
        long countAfterLike = quoteLikeRepository.countByQuoteId(quote.getId());
        System.out.println("좋아요 저장 후 좋아요 개수: " + countAfterLike);
        assertTrue(countAfterLike == 1);

        Optional<QuoteLike> found = quoteLikeRepository.findById(
                new QuoteLikeId(quote.getId(), user.getId())
        );
        assertTrue(found.isPresent());

        // 5. 좋아요 삭제
        quoteLikeRepository.deleteByMemberIdAndQuoteId(user.getId(), quote.getId());

        long countAfterDelete = quoteLikeRepository.countByQuoteId(quote.getId());
        System.out.println("좋아요 삭제 후 좋아요 개수: " + countAfterDelete);
        assertTrue(countAfterDelete == 0);

        Optional<QuoteLike> foundAfterDelete = quoteLikeRepository.findById(
                new QuoteLikeId(quote.getId(), user.getId())
        );
        assertTrue(foundAfterDelete.isEmpty());
    }

    @Test
    public void testNoDuplicateLikes() {
        // 1. 사용자 생성
        Members user = new Members();
        user.setNickname("중복테스터");
        user.setProvider(GOOGLE);
        user.setProviderId("dup_test123");
        user.setCreatedAt(LocalDateTime.now());
        memberRepository.save(user);

        // 2. 명언 생성
        Quote quote = new Quote();
        quote.setMember(user);
        quote.setBookName("중복 도서");
        quote.setContent("중복 테스트 명언");
        quote.setCreatedAt(LocalDateTime.now());
        quote.setLikeCount(0);
        quoteRepository.save(quote);

        // 3. 첫 좋아요 저장
        QuoteLike like1 = new QuoteLike();
        like1.setMember(user);
        like1.setQuote(quote);
        quoteLikeRepository.save(like1);

        // 4. 중복 좋아요 저장 시도
        QuoteLike like2 = new QuoteLike();
        like2.setMember(user);
        like2.setQuote(quote);
        try {
            quoteLikeRepository.save(like2);
        } catch (Exception e) {
            System.out.println("중복 좋아요 예외 (정상): " + e.getMessage());
        }

        // 5. 좋아요 개수 확인
        long count = quoteLikeRepository.countByQuoteId(quote.getId());
        System.out.println("좋아요 개수 (중복 방지): " + count);
        assertTrue(count == 1);
    }

    @Test
    public void testMultipleUsersLikeCount() {
        // 1. 작성자 생성
        Members author = new Members();
        author.setNickname("명언작성자");
        author.setProvider(GOOGLE);
        author.setProviderId("author_123");
        author.setCreatedAt(LocalDateTime.now());
        memberRepository.save(author);

        // 2. 명언 생성
        Quote quote = new Quote();
        quote.setMember(author);
        quote.setBookName("다인 도서");
        quote.setContent("여러 사용자 좋아요 테스트 명언");
        quote.setCreatedAt(LocalDateTime.now());
        quote.setLikeCount(0);
        quoteRepository.save(quote);

        // 3. 여러 사용자 생성 + 좋아요 저장
        int userCount = 5;
        for (int i = 1; i <= userCount; i++) {
            Members user = new Members();
            user.setNickname("사용자" + i);
            user.setProvider(GOOGLE);
            user.setProviderId("multi_user_" + i);
            user.setCreatedAt(LocalDateTime.now());
            memberRepository.save(user);

            QuoteLike like = new QuoteLike();
            like.setMember(user);
            like.setQuote(quote);
            quoteLikeRepository.save(like);
        }

        // 4. 좋아요 개수 검증
        long totalLikes = quoteLikeRepository.countByQuoteId(quote.getId());
        System.out.println("총 좋아요 수: " + totalLikes);
        assertTrue(totalLikes == userCount);
    }
}
