package org.example.crm_project.modules.quote_management.domain.repository;

import org.example.crm_project.modules.quote_management.domain.entity.Quote;

import java.util.List;
import java.util.Optional;

/**
 * Domain repository interface cho Quote.
 * Thuần Java - không phụ thuộc bất kỳ framework nào.
 * Implementation nằm ở infrastructure layer.
 */
public interface QuoteRepository {

    Quote save(Quote quote);

    Optional<Quote> findById(Integer id);

    List<Quote> findAllActive();

    List<Quote> searchByKeyword(String keyword);

    void delete(Quote quote);

    boolean existsByQuoteNumber(String quoteNumber);

    boolean existsByQuoteNumberExcludeId(String quoteNumber, Integer excludeId);

    long countByCustomerId(Integer customerId);
}
