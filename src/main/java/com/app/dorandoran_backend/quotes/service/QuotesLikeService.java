package com.app.dorandoran_backend.quotes.service;

import org.springframework.stereotype.Service;

import com.app.dorandoran_backend.mypage.Entity.Members;
import com.app.dorandoran_backend.quotes.Entity.QuoteLike;
import com.app.dorandoran_backend.quotes.Entity.QuotePost;
import com.app.dorandoran_backend.quotes.repository.QuoteLikeRepository;
import com.app.dorandoran_backend.quotes.repository.QuoteRepository;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QuotesLikeService {
	private final QuoteLikeRepository QuoteLikeRepository;
    private final QuoteRepository QuoteRepository;

    @Transactional
    public void likeQuote(Members member, QuotePost quote) {
        // 중복 체크
        if (QuoteLikeRepository.existsByQuoteAndMember(quote, member)) {
            throw new RuntimeException("이미 좋아요한 명언입니다.");
        }

        QuoteLike quoteLike = new QuoteLike();
        quoteLike.setMember(member);
        quoteLike.setQuote(quote);

        QuoteLikeRepository.save(quoteLike);

        // 리뷰의 좋아요 수 증가
        quote.setLikeCount(quote.getLikeCount() + 1);
        QuoteRepository.save(quote);
    }

    @Transactional
    public void unlikeQuote(Members member, QuotePost quote) {
    	QuoteLike quoteLike = QuoteLikeRepository.findByQuoteAndMember(quote, member)
                .orElseThrow(() -> new RuntimeException("좋아요한 적 없는 명언입니다."));

    	QuoteLikeRepository.delete(quoteLike);

        // 리뷰의 좋아요 수 감소
    	quote.setLikeCount(Math.max(0, quote.getLikeCount() - 1));
    	QuoteRepository.save(quote);
    }

    public boolean hasLiked(Members member, QuotePost quote) {
        return QuoteLikeRepository.existsByQuoteAndMember(quote, member);
    }

}
