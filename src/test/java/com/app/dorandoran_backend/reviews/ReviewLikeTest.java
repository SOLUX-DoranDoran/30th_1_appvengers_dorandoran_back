package com.app.dorandoran_backend.reviews;

import com.app.dorandoran_backend.home.entity.Books;
import com.app.dorandoran_backend.home.repository.BookRepository;
import com.app.dorandoran_backend.mypage.entity.Members;
import com.app.dorandoran_backend.mypage.repository.MemberRepository;
import com.app.dorandoran_backend.reviews.entity.ReviewLike;
import com.app.dorandoran_backend.reviews.entity.ReviewLikeId;
import com.app.dorandoran_backend.reviews.entity.ReviewPost;
import com.app.dorandoran_backend.reviews.repository.ReviewLikeRepository;
import com.app.dorandoran_backend.reviews.repository.ReviewPostRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static com.app.dorandoran_backend.mypage.entity.Provider.GOOGLE;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback
@ActiveProfiles("test")
public class ReviewLikeTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ReviewPostRepository reviewPostRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ReviewLikeRepository reviewLikeRepository;

    @Test
    public void testReviewLikeSave() {
        // given
        Members user = new Members();
        user.setNickname("테스터");
        user.setProvider(GOOGLE);
        user.setProviderId("test123");
        user.setCreatedAt(LocalDateTime.now());
        memberRepository.save(user);

        Books book = new Books();
        book.setTitle("테스트 도서");
        book.setAuthor("작가");
        book.setPublisher("출판사");
        book.setPublisherDate(LocalDate.now());
        bookRepository.save(book);

        ReviewPost review = new ReviewPost();
        review.setMember(user);
        review.setBook(book);
        review.setContent("내용");
        review.setCreatedAt(LocalDateTime.now());
        review.setRating((byte) 4);
        reviewPostRepository.save(review);

        // when - 좋아요 저장
        ReviewLike like = new ReviewLike();
        like.setMember(user);
        like.setReview(review);
        reviewLikeRepository.save(like);

        // 좋아요 개수 확인 및 출력
        long countAfterLike = reviewLikeRepository.countByReviewId(review.getId());
        System.out.println("좋아요 저장 후 좋아요 개수: " + countAfterLike);

        // then - 좋아요가 저장되었는지 확인
        Optional<ReviewLike> found = reviewLikeRepository.findById(
                new ReviewLikeId(user.getId(), review.getId())
        );
        assertTrue(found.isPresent());

        // when - 좋아요 삭제 (취소)
        reviewLikeRepository.deleteByMemberIdAndReviewId(user.getId(), review.getId());

        // 좋아요 개수 확인 및 출력
        long countAfterDelete = reviewLikeRepository.countByReviewId(review.getId());
        System.out.println("좋아요 삭제 후 좋아요 개수: " + countAfterDelete);

        // then - 좋아요가 삭제되었는지 확인
        Optional<ReviewLike> foundAfterDelete = reviewLikeRepository.findById(
                new ReviewLikeId(user.getId(), review.getId())
        );
        assertTrue(foundAfterDelete.isEmpty());
    }

    @Test
    public void testNoDuplicateLikes() {
        // given
        Members user = new Members();
        user.setNickname("중복테스터");
        user.setProvider(GOOGLE);
        user.setProviderId("dup_test123");
        user.setCreatedAt(LocalDateTime.now());
        memberRepository.save(user);

        Books book = new Books();
        book.setTitle("중복 테스트 도서");
        book.setAuthor("작가");
        book.setPublisher("출판사");
        book.setPublisherDate(LocalDate.now());
        bookRepository.save(book);

        ReviewPost review = new ReviewPost();
        review.setMember(user);
        review.setBook(book);
        review.setContent("중복 테스트 내용");
        review.setCreatedAt(LocalDateTime.now());
        review.setRating((byte) 5);
        reviewPostRepository.save(review);

        // when - 첫 좋아요 저장
        ReviewLike like1 = new ReviewLike();
        like1.setMember(user);
        like1.setReview(review);
        reviewLikeRepository.save(like1);

        // when - 동일 사용자로 두 번째 좋아요 저장 시도
        ReviewLike like2 = new ReviewLike();
        like2.setMember(user);
        like2.setReview(review);
        // 중복 저장 시 예외 발생할 수 있으니 try-catch
        try {
            reviewLikeRepository.save(like2);
        } catch (Exception e) {
            System.out.println("중복 좋아요 저장 시도에서 예외 발생 (의도된 상황): " + e.getMessage());
        }

        // then - 좋아요 개수는 1개
        long count = reviewLikeRepository.countByReviewId(review.getId());
        System.out.println("중복 좋아요 시도 후 좋아요 개수: " + count);
        assertTrue(count == 1);
    }

    @Test
    public void testMultipleUsersLikeCount() {
        // 1. 리뷰 작성자 생성 및 저장
        Members reviewAuthor = new Members();
        reviewAuthor.setNickname("리뷰작성자");
        reviewAuthor.setProvider(GOOGLE);
        reviewAuthor.setProviderId("author_123");
        reviewAuthor.setCreatedAt(LocalDateTime.now());
        memberRepository.save(reviewAuthor);

        // 2. 책 생성 및 저장
        Books book = new Books();
        book.setTitle("여러 사용자 좋아요 도서");
        book.setAuthor("작가");
        book.setPublisher("출판사");
        book.setPublisherDate(LocalDate.now());
        bookRepository.save(book);

        // 3. 리뷰 생성 및 저장 (작성자와 책 세팅)
        ReviewPost review = new ReviewPost();
        review.setMember(reviewAuthor);
        review.setBook(book);
        review.setContent("여러 사용자 좋아요 테스트");
        review.setCreatedAt(LocalDateTime.now());
        review.setRating((byte) 5);
        reviewPostRepository.save(review);

        // 4. 여러 사용자 생성 및 좋아요 저장
        int userCount = 5;
        for (int i = 1; i <= userCount; i++) {
            Members user = new Members();
            user.setNickname("사용자" + i);
            user.setProvider(GOOGLE);
            user.setProviderId("multi_user_" + i);
            user.setCreatedAt(LocalDateTime.now());
            memberRepository.save(user);

            ReviewLike like = new ReviewLike();
            like.setMember(user);
            like.setReview(review);
            reviewLikeRepository.save(like);
        }

        // 5. 좋아요 개수 조회 및 검증
        long totalLikes = reviewLikeRepository.countByReviewId(review.getId());
        System.out.println("여러 사용자 좋아요 개수: " + totalLikes);
        assertTrue(totalLikes == userCount);
    }

}

