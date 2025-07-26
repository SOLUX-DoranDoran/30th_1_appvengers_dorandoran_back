package com.app.dorandoran_backend.debate.dto;

import lombok.Builder;
import lombok.Getter;
import com.app.dorandoran_backend.debate.entity.DebateRoom;

import java.time.LocalDateTime;

@Getter
@Builder
public class DebateDto {
    private Long boardId;
    private Long bookId;
    private Long memberId;
    private String title;
    private String content;
    private LocalDateTime createdAt;

    // Entity → DTO 변환용 정적 팩토리 메서드
    public static DebateDto from(DebateRoom debateRoom) {
        return DebateDto.builder()
                .boardId(debateRoom.getId())
                .bookId(debateRoom.getBook() != null ? debateRoom.getBook().getId() : null)
                .memberId(debateRoom.getMember() != null ? debateRoom.getMember().getId() : null)
                .title(debateRoom.getTitle())
                .content(debateRoom.getContent())
                .createdAt(debateRoom.getCreatedAt())
                .build();
    }
}
