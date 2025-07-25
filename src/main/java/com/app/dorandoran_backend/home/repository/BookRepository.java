package com.app.dorandoran_backend.home.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.dorandoran_backend.home.entity.Books;

public interface BookRepository extends JpaRepository<Books, Long> {
	   Optional<Books> findByTitle(String title); 
}
