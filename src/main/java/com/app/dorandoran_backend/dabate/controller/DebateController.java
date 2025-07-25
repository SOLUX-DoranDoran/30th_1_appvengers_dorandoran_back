package com.app.dorandoran_backend.dabate.controller;

import com.app.dorandoran_backend.debate.service.DebateService;
import com.app.dorandoran_backend.debate.dto.CommentCreateDto;
import com.app.dorandoran_backend.debate.dto.CommentDto;
import com.app.dorandoran_backend.debate.dto.DebateDto;
import com.app.dorandoran_backend.debate.service.CommentService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class DebateController {

    private final DebateService boardService;
    private final CommentService commentService;

    // --- Board (토론) 관련 ---

    // 전체 토론 목록 조회
    @GetMapping("/boards")
    public ResponseEntity<Page<DebateDto>> getAllBoards(
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "size", defaultValue = "10") int size) {

        Page<DebateDto> boards = boardService.getAllBoards(page, size);
        return ResponseEntity.ok(boards);
    }

    // 도서별 토론 목록 조회
    @GetMapping("/books/{bookId}/boards")
    public ResponseEntity<Page<DebateDto>> getBoardsByBook(
            @PathVariable Long bookId,
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "size", defaultValue = "10") int size) {

        Page<DebateDto> boards = boardService.getBoardsByBook(bookId, page, size);
        return ResponseEntity.ok(boards);
    }

    // 토론 상세 조회
    @GetMapping("/boards/{boardId}")
    public ResponseEntity<DebateDto> getBoard(@PathVariable Long boardId) {
        DebateDto boardDto = boardService.getBoardById(boardId);
        return ResponseEntity.ok(boardDto);
    }

    // 댓글/대댓글 목록 조회
    @GetMapping("/boards/{boardId}/comments")
    public ResponseEntity<Page<CommentDto>> getComments(
            @PathVariable Long boardId,
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "size", defaultValue = "10") int size) {

        Page<CommentDto> comments = commentService.getComments(boardId, page, size);
        return ResponseEntity.ok(comments);
    }

    // 댓글 또는 대댓글 작성
    @PostMapping("/boards/{boardId}/comments")
    public ResponseEntity<CommentDto> createComment(
            @PathVariable Long boardId,
            @Valid @RequestBody CommentCreateDto dto) {

        CommentDto created = commentService.createComment(boardId, dto);
        return ResponseEntity.ok(created);
    }
    
    @GetMapping("/boards/recent")
    public ResponseEntity<List<DebateDto>> getRecentBoards() {
        int limit = 5;
        List<DebateDto> recentBoards = boardService.getRecentBoards(limit);
        return ResponseEntity.ok(recentBoards);
    }
}
