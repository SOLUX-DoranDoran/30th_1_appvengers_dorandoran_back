package com.app.dorandoran_backend.boards.controller;

import com.app.dorandoran_backend.boards.dto.BoardDto;
import com.app.dorandoran_backend.boards.service.BoardService;
import com.app.dorandoran_backend.boards.dto.CommentCreateDto;
import com.app.dorandoran_backend.boards.dto.CommentDto;
import com.app.dorandoran_backend.boards.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    private final CommentService commentService;

    // --- Board (토론) 관련 ---

    // 전체 토론 목록 조회
    @GetMapping("/boards")
    public ResponseEntity<Page<BoardDto>> getAllBoards(
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "size", defaultValue = "10") int size) {

        Page<BoardDto> boards = boardService.getAllBoards(page, size);
        return ResponseEntity.ok(boards);
    }

    // 도서별 토론 목록 조회
    @GetMapping("/books/{bookId}/boards")
    public ResponseEntity<Page<BoardDto>> getBoardsByBook(
            @PathVariable Long bookId,
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "size", defaultValue = "10") int size) {

        Page<BoardDto> boards = boardService.getBoardsByBook(bookId, page, size);
        return ResponseEntity.ok(boards);
    }

    // 토론 상세 조회
    @GetMapping("/boards/{boardId}")
    public ResponseEntity<BoardDto> getBoard(@PathVariable Long boardId) {
        BoardDto boardDto = boardService.getBoardById(boardId);
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
}
