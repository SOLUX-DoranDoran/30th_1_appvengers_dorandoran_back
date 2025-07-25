package com.app.dorandoran_backend.debate.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

import com.app.dorandoran_backend.debate.entity.DebateComments;

@Getter
@Builder
public class CommentDto {

    private Long id;
    private String content;
    private LocalDateTime createdAt;
    private String memberNickname;
    private Long parentId;

    public static CommentDto from(DebateComments comment) {
        return CommentDto.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .createdAt(comment.getCreatedAt())
                .memberNickname(comment.getMember().getNickname())
                .parentId(comment.getParent() != null ? comment.getParent().getId() : null)
                .build();
    }
}
