package com.app.dorandoran_backend.home.repository;

import com.app.dorandoran_backend.home.Entity.Books;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Books, Long> {
}
