package com.app.dorandoran_backend.debate.repository;

import com.app.dorandoran_backend.debate.entity.DebateComments;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DebateCommentsRepository extends JpaRepository<DebateComments, Long> {

    // debate_comment 엔티티에 room 필드가 있고 room.id로 조회
    Page<DebateComments> findByRoomId(Long roomId, Pageable pageable);

}
