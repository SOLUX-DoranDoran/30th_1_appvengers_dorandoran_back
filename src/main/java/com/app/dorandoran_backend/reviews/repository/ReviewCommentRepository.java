package com.app.dorandoran_backend.reviews.repository;

<<<<<<< HEAD
=======
import com.app.dorandoran_backend.reviews.entity.ReviewComment;
import com.app.dorandoran_backend.reviews.entity.ReviewPost;
>>>>>>> main
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.app.dorandoran_backend.reviews.entity.ReviewComment;
import com.app.dorandoran_backend.reviews.entity.ReviewPost;

public interface ReviewCommentRepository extends JpaRepository<ReviewComment, Long> {
    Page<ReviewComment> findAllByReview(ReviewPost review, Pageable pageable);
}
