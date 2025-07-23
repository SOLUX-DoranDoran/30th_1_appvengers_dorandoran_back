package com.app.dorandoran_backend.boards.repository;

import com.app.dorandoran_backend.boards.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {

    // 도서별로 토론방 목록 조회
    Page<Board> findByBookId(Long bookId, Pageable pageable);
}
