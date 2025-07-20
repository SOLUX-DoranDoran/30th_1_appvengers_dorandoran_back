package com.app.dorandoran_backend.auth.service;

import com.app.dorandoran_backend.mypage.Entity.Members;
import com.app.dorandoran_backend.mypage.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberAuthService {
    private final MemberRepository memberRepository;
/*
    private final ReviewPostRepository reviewPostRepository;
    private final ReviewLikeRepository reviewLikeRepository;
    private final ReviewCommentRepository reviewCommentRepository;
    private final QuoteRepository quoteRepository;
    private final QuoteLikeRepository quoteLikeRepository;
    private final DebateRoomRepository debateRoomRepository;
    private final DebateMessageRepository debateMessageRepository;
    private final DebateCommentsRepository debateCommentsRepository;
*/

    public Members findByProviderId(String providerId) {
        return memberRepository.findByProviderId(providerId)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저를 찾을 수 없습니다."));
    }

    public void removeRefreshToken(String providerId) {
        Optional<Members> optionalMember = memberRepository.findByProviderId(providerId);
        if (optionalMember.isEmpty()) {
            // 회원을 못 찾으면 그냥 로그 출력 후 리턴
            log.warn("회원이 없어 refreshToken 제거 불가: providerId={}", providerId);
            return;
        }

        Members member = optionalMember.get();

        if (member.getRefreshToken() == null) {
            log.info("이미 로그아웃된 회원입니다: providerId={}", providerId);
            return;
        }

        member.setRefreshToken(null);
        memberRepository.save(member);
    }

/*
    public void deleteMember(String providerId) {
        Members member = findByProviderId(providerId);

        // 1. ReviewPost의 member 참조 해제
        List<ReviewPost> reviews = reviewPostRepository.findByMember(member);
        for (ReviewPost review : reviews) {
            review.setMember(null);
        }
        reviewPostRepository.saveAll(reviews);

        // 2. ReviewLike 삭제 또는 참조 해제
        List<ReviewLike> reviewLikes = reviewLikeRepository.findByMember(member);
        for (ReviewLike like : reviewLikes) {
            like.setMember(null);
        }
        reviewLikeRepository.saveAll(reviewLikes);

        // 3. ReviewComment 삭제 또는 참조 해제
        List<ReviewComment> comments = reviewCommentRepository.findByMember(member);
        for (ReviewComment comment : comments) {
            comment.setMember(null);
        }
        reviewCommentRepository.saveAll(comments);

        // 4. Quote 처리
        List<Quote> quotes = quoteRepository.findByMember(member);
        for (Quote quote : quotes) {
            quote.setMember(null);
        }
        quoteRepository.saveAll(quotes);

        // 5. QuoteLike 처리
        List<QuoteLike> quoteLikes = quoteLikeRepository.findByMember(member);
        for (QuoteLike like : quoteLikes) {
            like.setMember(null);
        }
        quoteLikeRepository.saveAll(quoteLikes);

        // 6. DebateRoom 처리
        List<DebateRoom> debateRooms = debateRoomRepository.findByMember(member);
        for (DebateRoom room : debateRooms) {
            room.setMember(null);
        }
        debateRoomRepository.saveAll(debateRooms);

        // 7. DebateMessage 처리
        List<DebateMessage> debateMessages = debateMessageRepository.findByMember(member);
        for (DebateMessage message : debateMessages) {
            message.setMember(null);
        }
        debateMessageRepository.saveAll(debateMessages);

        // 8. DebateComments 처리
        List<DebateComments> debateComments = debateCommentsRepository.findByMember(member);
        for (DebateComments comment : debateComments) {
            comment.setMember(null);
        }
        debateCommentsRepository.saveAll(debateComments);

        // 마지막으로 회원 삭제
        memberRepository.delete(member);
    }
*/
}
