package com.app.dorandoran_backend.debate.service;

import com.app.dorandoran_backend.debate.dto.DebateCreateDto;
import com.app.dorandoran_backend.debate.dto.DebateDto;
import com.app.dorandoran_backend.debate.entity.DebateMessage;
import com.app.dorandoran_backend.debate.entity.DebateRoom;
import com.app.dorandoran_backend.debate.repository.DebateRoomRepository;
import com.app.dorandoran_backend.home.entity.Books;
import com.app.dorandoran_backend.home.repository.BookRepository;
import com.app.dorandoran_backend.mypage.entity.Members;
import com.app.dorandoran_backend.mypage.service.MemberService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DebateService {

    private final DebateRoomRepository debateRoomRepository;
    private final BookRepository bookRepository;
    private final MemberService memberService;

    @Transactional
    public DebateDto createBoard(DebateCreateDto dto) {
        Members member = memberService.getCurrentMember();

        DebateRoom.DebateRoomBuilder builder = DebateRoom.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .member(member)
                .createdAt(LocalDateTime.now());

        // 책 제목으로 책 찾기
        if (dto.getBookTitle() != null && !dto.getBookTitle().isBlank()) {
            Books book = bookRepository.findByTitle(dto.getBookTitle())
                    .orElseThrow(() -> new RuntimeException("해당 제목의 도서를 찾을 수 없습니다."));
            builder.book(book);
        }

        DebateRoom debateRoom = builder.build();
        debateRoomRepository.save(debateRoom);

        return DebateDto.from(debateRoom);
    }


    // 전체 토론 목록 조회
    public Page<DebateDto> getAllBoards(int page, int size) {
        if(page < 1) page = 1;
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<DebateRoom> boardsPage = debateRoomRepository.findAll(pageable);
        return boardsPage.map(DebateDto::from);
    }

    // 도서별 토론 목록 조회
    public Page<DebateDto> getBoardsByBook(Long bookId, int page, int size) {
        if(page < 1) page = 1;
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<DebateRoom> boardsPage = debateRoomRepository.findByBookId(bookId, pageable);
        return boardsPage.map(DebateDto::from);
    }

    // 단일 토론 상세 조회
    public DebateDto getBoardById(Long boardId) {
        DebateRoom debateRoom = debateRoomRepository.findById(boardId)
                .orElseThrow(() -> new RuntimeException("토론을 찾을 수 없습니다. id=" + boardId));
        return DebateDto.from(debateRoom);
    }

    // 최근 토론 목록 조회 (limit 개수)
    public List<DebateDto> getRecentBoards(int limit) {
        Pageable pageable = PageRequest.of(0, limit, Sort.by(Sort.Direction.DESC, "createdAt"));
        return debateRoomRepository.findAll(pageable)
                .map(DebateDto::from)
                .getContent();
    }
}
