package com.app.dorandoran_backend.quotes.repository;

import com.app.dorandoran_backend.quotes.Entity.Quote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuoteRepository extends JpaRepository<Quote, Long> {
}
