package org.example.crm_project.modules.customers.domain.repository;

import org.example.crm_project.modules.customers.domain.entity.Quote;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Repository Interface: QuoteRepository
 */
public interface QuoteRepository {
    Quote save(Quote quote);
    Optional<Quote> findById(Long id);
    Optional<Quote> findByQuoteCode(String quoteCode);
    Page<Quote> findByCustomerId(Long customerId, Pageable pageable);
    Page<Quote> findByStatus(String status, Pageable pageable);
    void delete(Long id);
    boolean existsById(Long id);
    long count();
}
