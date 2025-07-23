package com.app.dorandoran_backend.boards.service;

import com.app.dorandoran_backend.boards.dto.CommentCreateDto;
import com.app.dorandoran_backend.boards.dto.CommentDto;
import com.app.dorandoran_backend.boards.entity.Board;
import com.app.dorandoran_backend.boards.entity.BoardComment;
import com.app.dorandoran_backend.boards.entity.BoardComment.BoardCommentBuilder;
import com.app.dorandoran_backend.boards.repository.BoardRepository;
import com.app.dorandoran_backend.boards.repository.CommentRepository;
import com.app.dorandoran_backend.mypage.Entity.Members;
import com.app.dorandoran_backend.mypage.service.MemberService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;
    private final MemberService memberService;

    @Transactional
    public CommentDto createComment(Long boardId, CommentCreateDto dto) {
        Members member = memberService.getCurrentMember();

        Board board = boardRepository.findById(boardId) 
                .orElseThrow(() -> new RuntimeException("토론이 존재하지 않습니다."));

        BoardCommentBuilder builder = BoardComment.builder()
                .board(board)
                .member(member)
                .content(dto.getContent())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now());

        if (dto.getParentId() != null) {
            BoardComment parentComment = commentRepository.findById(dto.getParentId())
                    .orElseThrow(() -> new RuntimeException("부모 댓글이 존재하지 않습니다."));
            builder.parent(parentComment);
        }

        BoardComment comment = builder.build();
        BoardComment saved = commentRepository.save(comment);
        return CommentDto.from(saved);
    }

    public Page<CommentDto> getComments(Long boardId, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        return commentRepository.findByBoardId(boardId, pageable)
                .map(CommentDto::from);
    }
}
