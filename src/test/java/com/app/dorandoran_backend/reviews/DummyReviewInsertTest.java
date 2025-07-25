package com.app.dorandoran_backend.reviews;

import com.app.dorandoran_backend.home.entity.Books;
import com.app.dorandoran_backend.home.repository.BookRepository;
import com.app.dorandoran_backend.mypage.entity.Members;
import com.app.dorandoran_backend.mypage.repository.MemberRepository;
import com.app.dorandoran_backend.reviews.entity.ReviewPost;
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
        // 1. 모든 책 가져오기
        List<Books> books = bookRepository.findAll();

        if (books.isEmpty()) {
            throw new RuntimeException("리뷰를 추가할 책이 없습니다.");
        }

        // 2. ID 1, 2번 유저 조회
        Members member1 = memberRepository.findById(1L)
                .orElseThrow(() -> new RuntimeException("ID가 1인 회원이 존재하지 않습니다."));
        Members member2 = memberRepository.findById(2L)
                .orElseThrow(() -> new RuntimeException("ID가 2인 회원이 존재하지 않습니다."));

        // 3. 책당 2개(각 유저당 1개) 리뷰 생성
        List<ReviewPost> reviews = books.stream()
                .flatMap(book -> List.of(
                        createReview(member1, book, "정말 유익하게 읽었어요! 👍", (byte) 5),
                        createReview(member2, book, "생각할 거리를 주는 책이었어요.", (byte) 4)
                ).stream()).toList();

        // 4. 저장
        reviewPostRepository.saveAll(reviews);

        System.out.printf("책 %d권에 대해 총 %d개의 리뷰가 저장되었습니다.%n", books.size(), reviews.size());
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
