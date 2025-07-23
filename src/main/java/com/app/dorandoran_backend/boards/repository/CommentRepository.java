package com.app.dorandoran_backend.boards.repository;

import com.app.dorandoran_backend.boards.entity.BoardComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<BoardComment, Long> {

    Page<BoardComment> findByBoardId(Long boardId, Pageable pageable);
}
