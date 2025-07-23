package com.app.dorandoran_backend.boards.dto;

import com.app.dorandoran_backend.boards.entity.BoardComment;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class CommentDto {

    private Long id;
    private String content;
    private LocalDateTime createdAt;
    private String memberNickname;
    private Long parentId;

    public static CommentDto from(BoardComment comment) {
        return CommentDto.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .createdAt(comment.getCreatedAt())
                .memberNickname(comment.getMember().getNickname())
                .parentId(comment.getParent() != null ? comment.getParent().getId() : null)
                .build();
    }
}
