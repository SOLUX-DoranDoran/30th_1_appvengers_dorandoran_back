package com.app.dorandoran_backend.boards.dto;

import com.app.dorandoran_backend.boards.entity.Board;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class BoardDto {
    private Long id;
    private Long bookId;
    private Long memberId;
    private String title;
    private String content;
    private LocalDateTime createdAt;

    // Entity → DTO 변환용 정적 팩토리 메서드
    public static BoardDto from(Board board) {
        return BoardDto.builder()
                .id(board.getId())
                .bookId(board.getBook() != null ? board.getBook().getId() : null)
                .memberId(board.getMember() != null ? board.getMember().getId() : null)
                .title(board.getTitle())
                .content(board.getContent())
                .createdAt(board.getCreatedAt())
                .build();
    }
}
