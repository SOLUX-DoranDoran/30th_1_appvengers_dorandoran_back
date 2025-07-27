package com.app.dorandoran_backend.debate.dto;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentCreateDto {
	@NotBlank(message = "댓글 내용을 입력해 주세요.")
    private String content;

    private Long parentId; // 대댓글일 경우 부모 댓글 ID (없으면 일반 댓글)

}
