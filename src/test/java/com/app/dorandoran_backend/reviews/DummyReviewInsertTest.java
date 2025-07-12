package com.app.dorandoran_backend.reviews;

import com.app.dorandoran_backend.home.Entity.Books;
import com.app.dorandoran_backend.home.repository.BookRepository;
import com.app.dorandoran_backend.mypage.Entity.Members;
import com.app.dorandoran_backend.mypage.Entity.Provider;
import com.app.dorandoran_backend.mypage.repository.MemberRepository;
import com.app.dorandoran_backend.reviews.Entity.ReviewPost;
import com.app.dorandoran_backend.reviews.repository.ReviewPostRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
public class DummyReviewInsertTest {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ReviewPostRepository reviewPostRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void insertDummyReviewsForBook1() {
        // 1. 책 ID = 1
        Books book = bookRepository.findById(1L)
                .orElseThrow(() -> new RuntimeException("책을 찾을 수 없습니다."));

        // 2. 테스트용 멤버 1명 조회 또는 생성 (이메일로 조회 예시)
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

        // 3. 더미 리뷰 리스트 생성
        List<ReviewPost> reviews = List.of(
                createReview(member, book, "정말 감동적인 이야기였어요. 별 다섯 개!", (byte) 5),
                createReview(member, book, "재밌긴 했지만 전개가 조금 아쉬웠어요.", (byte) 3),
                createReview(member, book, "몰입감 최고! 하루 만에 다 읽었어요.", (byte) 4)
        );

        // 4. 저장
        reviewPostRepository.saveAll(reviews);

        System.out.println("더미 리뷰 3개가 성공적으로 추가되었습니다.");
    }

    private ReviewPost createReview(Members member, Books book, String content, byte rating) {
        ReviewPost review = new ReviewPost();
        review.setMember(member);
        review.setBook(book);
        review.setContent(content);
        review.setRating(rating);
        review.setCreatedAt(LocalDateTime.now());
        review.setLikeCount(0);
        review.setCommentCount(0);
        return review;
    }
}
