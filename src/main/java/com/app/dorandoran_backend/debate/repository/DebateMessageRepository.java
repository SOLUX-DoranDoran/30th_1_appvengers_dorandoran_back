package com.app.dorandoran_backend.debate.repository;

import com.app.dorandoran_backend.debate.Entity.DebateMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DebateMessageRepository extends JpaRepository<DebateMessage, Long> {
    // 토론방 ID로 해당 토론방의 모든 메시지 조회
    List<DebateMessage> findByRoomId(Long roomId);
}
