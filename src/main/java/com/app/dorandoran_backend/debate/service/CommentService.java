package com.app.dorandoran_backend.debate.service;

import com.app.dorandoran_backend.debate.entity.DebateRoom;
import com.app.dorandoran_backend.debate.entity.DebateComments;
import com.app.dorandoran_backend.debate.dto.CommentCreateDto;
import com.app.dorandoran_backend.debate.dto.CommentDto;
import com.app.dorandoran_backend.debate.repository.DebateCommentsRepository;
import com.app.dorandoran_backend.debate.repository.DebateRoomRepository;
import com.app.dorandoran_backend.mypage.entity.Members;
import com.app.dorandoran_backend.mypage.service.MemberService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentService {

    private final DebateCommentsRepository commentRepository;
    private final DebateRoomRepository debateRoomRepository;
    private final MemberService memberService;

    @Transactional
    public CommentDto createComment(Long debateRoomId, CommentCreateDto dto) {
        Members member = memberService.getCurrentMember();

        DebateRoom room = debateRoomRepository.findById(debateRoomId) 
                .orElseThrow(() -> new RuntimeException("토론방이 존재하지 않습니다."));

        DebateComments.DebateCommentsBuilder builder = DebateComments.builder()
        	    .room(room)
        	    .member(member)
        	    .content(dto.getContent())
        	    .updatedAt(LocalDateTime.now());

        log.info("Saving comment: roomId={}, memberId={}, content={}", room.getId(), member.getId(), dto.getContent());

        if (dto.getParentId() != null) {
            DebateComments parentComment = commentRepository.findById(dto.getParentId())
                    .orElseThrow(() -> new RuntimeException("부모 댓글이 존재하지 않습니다."));
            builder.parent(parentComment);
        }

        DebateComments comment = builder.build();
        DebateComments saved = commentRepository.save(comment);
        return CommentDto.from(saved);
    }

    public Page<CommentDto> getComments(Long debateRoomId, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        return commentRepository.findByRoomId(debateRoomId, pageable)
                .map(CommentDto::from);
    }
}
