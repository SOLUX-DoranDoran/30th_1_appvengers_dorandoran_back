package com.app.dorandoran_backend.debate.repository;

import com.app.dorandoran_backend.debate.entity.DebateComments;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DebateCommentsRepository extends JpaRepository<DebateComments, Long> {
}
