package com.app.dorandoran_backend.reviews.repository;

import com.app.dorandoran_backend.reviews.Entity.ReviewPost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewPostRepository extends JpaRepository<ReviewPost, Long> {
}
