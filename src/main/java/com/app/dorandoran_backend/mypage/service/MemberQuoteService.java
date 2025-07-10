package com.app.dorandoran_backend.mypage.service;

import com.app.dorandoran_backend.mypage.Entity.Members;
import com.app.dorandoran_backend.quotes.dto.QuoteDto;
import com.app.dorandoran_backend.quotes.repository.QuoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberQuoteService {

    private final QuoteRepository quoteRepository;

    public List<QuoteDto> findQuotesByMember(Members member) {
        return quoteRepository.findByMemberOrderByCreatedAtDesc(member)
                .stream()
                .map(QuoteDto::from)
                .toList();
    }
}
