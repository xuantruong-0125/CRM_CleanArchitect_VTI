package org.example.crm_project.modules.quote_management.domain.repository;

import org.example.crm_project.modules.quote_management.domain.entity.Quote;

import org.example.crm_project.shared.model.PageResult;
import java.util.Optional;

/**
 * Domain repository interface cho Quote.
 * Thuần Java - không phụ thuộc bất kỳ framework nào.
 * Implementation nằm ở infrastructure layer.
 */
public interface QuoteRepository {

    Quote save(Quote quote);

    Optional<Quote> findById(Integer id);

    PageResult<Quote> findAllActive(int page, int size);

    PageResult<Quote> findActiveByCustomerId(Long customerId, int page, int size);

    PageResult<Quote> searchByKeyword(String keyword, int page, int size);

    PageResult<Quote> searchQuotes(String keyword, Long customerId, Integer statusId, String quoteNumber, java.time.LocalDateTime fromDate, java.time.LocalDateTime toDate, int page, int size, String sortField, String sortDir);

    void delete(Quote quote);

    boolean existsByQuoteNumber(String quoteNumber);

    boolean existsByQuoteNumberExcludeId(String quoteNumber, Integer excludeId);

    long countByCustomerId(Long customerId);
    void updateTotalAmount(Integer id, java.math.BigDecimal totalAmount);

    void updateQuoteNumber(Integer id, String quoteNumber);
}
