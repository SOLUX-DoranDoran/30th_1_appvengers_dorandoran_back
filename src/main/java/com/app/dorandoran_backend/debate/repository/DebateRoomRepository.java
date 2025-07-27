package com.app.dorandoran_backend.debate.repository;

import com.app.dorandoran_backend.debate.entity.DebateRoom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DebateRoomRepository extends JpaRepository<DebateRoom, Long> {

    // 도서별로 토론방 목록 조회
    Page<DebateRoom> findByBookId(Long bookId, Pageable pageable);
}
