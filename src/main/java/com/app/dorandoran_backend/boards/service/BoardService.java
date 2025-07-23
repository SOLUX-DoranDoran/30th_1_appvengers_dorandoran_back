package com.app.dorandoran_backend.boards.service;

import com.app.dorandoran_backend.boards.dto.BoardCreateDto;
import com.app.dorandoran_backend.boards.dto.BoardDto;
import com.app.dorandoran_backend.boards.entity.Board;
import com.app.dorandoran_backend.boards.repository.BoardRepository;
import com.app.dorandoran_backend.home.Entity.Books;
import com.app.dorandoran_backend.home.repository.BookRepository;
import com.app.dorandoran_backend.mypage.Entity.Members;
import com.app.dorandoran_backend.mypage.service.MemberService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final BookRepository bookRepository;
    private final MemberService memberService;

    @Transactional
    public BoardDto createBoard(BoardCreateDto dto) {
        Members member = memberService.getCurrentMember();

        Board.BoardBuilder builder = Board.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .member(member)
                .createdAt(LocalDateTime.now());

        if (dto.getBookId() != null) {
            Books book = bookRepository.findById(dto.getBookId())
                    .orElseThrow(() -> new RuntimeException("해당 도서를 찾을 수 없습니다."));
            builder.book(book);
        }
        Board board = builder.build();
        boardRepository.save(board);
        return BoardDto.from(board);
    }
 // 전체 토론 목록 조회
    public Page<BoardDto> getAllBoards(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<Board> boardsPage = boardRepository.findAll(pageable);
        return boardsPage.map(BoardDto::from); // entity -> dto 변환
    }

    // 도서별 토론 목록 조회
    public Page<BoardDto> getBoardsByBook(Long bookId, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<Board> boardsPage = boardRepository.findByBookId(bookId, pageable);
        return boardsPage.map(BoardDto::from);
    }
    
    public BoardDto getBoardById(Long boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new RuntimeException("토론을 찾을 수 없습니다. id=" + boardId));
        return BoardDto.from(board);
    }
}
