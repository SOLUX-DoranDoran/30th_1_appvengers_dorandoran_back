package com.app.dorandoran_backend.quotes.service;

import com.app.dorandoran_backend.home.entity.Books;
import com.app.dorandoran_backend.home.repository.BookRepository;
import com.app.dorandoran_backend.mypage.entity.Members;
import com.app.dorandoran_backend.quotes.dto.QuoteDto;
import com.app.dorandoran_backend.quotes.entity.QuotePost;
import com.app.dorandoran_backend.quotes.repository.QuoteRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QuoteService {

    private final QuoteRepository quoteRepository;
    private final BookRepository bookRepository;

    public QuoteDto getQuoteById(Long quoteId) {
    	QuotePost quote = quoteRepository.findById(quoteId)
                .orElseThrow(() -> new RuntimeException("리뷰를 찾을 수 없습니다."));
        return QuoteDto.from(quote);
    }

    public List<QuoteDto> getQuoteList(String sort, int page, int size) {
        Sort sortOption = switch (sort) {
            case "rating" -> Sort.by(Sort.Direction.DESC, "rating");
            default -> Sort.by(Sort.Direction.DESC, "createdAt");
        };
        Pageable pageable = PageRequest.of(page - 1, size, sortOption);
        return quoteRepository.findAll(pageable)
                .stream().map(QuoteDto::from).toList();
    }
    
    public List<QuoteDto> getRecentQuotes() {
        return quoteRepository.findTop10ByOrderByCreatedAtDesc()
                .stream()
                .map(QuoteDto::from)
                .toList();
    }
    
    @Transactional
    public Long createQuote(Members member, QuoteDto quoteDto) {
        Books book = bookRepository.findById(quoteDto.getBookId())
                .orElseThrow(() -> new IllegalArgumentException("해당 책이 없습니다. 책 ID: " + quoteDto.getBookId()));

        QuotePost quotePost = new QuotePost();
        quotePost.setContent(quoteDto.getContent());
        quotePost.setMember(member);
        quotePost.setBook(book);
        quotePost.setBookName(book.getTitle());
        quotePost.setCreatedAt(LocalDateTime.now());
        quotePost.setLikeCount(0);

        QuotePost savedQuote = quoteRepository.save(quotePost);
        return savedQuote.getId();
    }

}

