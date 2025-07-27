package com.app.dorandoran_backend.mypage.service;

import com.app.dorandoran_backend.mypage.entity.Members;
import com.app.dorandoran_backend.reviews.dto.ReviewDto;
import com.app.dorandoran_backend.reviews.repository.ReviewPostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberReviewService {

    private final ReviewPostRepository reviewPostRepository;

    public List<ReviewDto> findReviewsByMember(Members member) {
        return reviewPostRepository.findByMemberOrderByCreatedAtDesc(member)
                .stream()
                .map(ReviewDto::from)
                .toList();
    }
}
