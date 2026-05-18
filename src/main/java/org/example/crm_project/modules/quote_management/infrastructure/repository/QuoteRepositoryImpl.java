package org.example.crm_project.modules.quote_management.infrastructure.repository;

import lombok.RequiredArgsConstructor;
import org.example.crm_project.modules.quote_management.domain.entity.Quote;
import org.example.crm_project.modules.quote_management.domain.repository.QuoteRepository;
import org.example.crm_project.modules.quote_management.infrastructure.persistence.mapper.QuoteJpaMapper;
import org.example.crm_project.modules.quote_management.infrastructure.persistence.repository.JpaQuoteRepository;
import org.example.crm_project.shared.model.PageResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.example.crm_project.modules.quote_management.infrastructure.persistence.entity.QuoteEntity;

import java.util.List;
import java.util.Optional;

/**
 * Implementation của QuoteRepository (domain interface).
 * Kết nối domain layer với Spring Data JPA.
 */
@Repository
@RequiredArgsConstructor
public class QuoteRepositoryImpl implements QuoteRepository {

    private final JpaQuoteRepository jpaRepository;

    @Override
    public Quote save(Quote quote) {
        return QuoteJpaMapper.toDomain(
                jpaRepository.save(QuoteJpaMapper.toEntity(quote)));
    }

    @Override
    public Optional<Quote> findById(Integer id) {
        return jpaRepository.findActiveById(id)
                .map(QuoteJpaMapper::toDomain);
    }

    @Override
    public PageResult<Quote> findAllActive(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<QuoteEntity> entityPage = jpaRepository.findAllActive(pageable);
        return mapToPageResult(entityPage);
    }

    @Override
    public PageResult<Quote> findActiveByCustomerId(Long customerId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<QuoteEntity> entityPage = jpaRepository.findActiveByCustomerId(customerId, pageable);
        return mapToPageResult(entityPage);
    }

    @Override
    public PageResult<Quote> searchByKeyword(String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<QuoteEntity> entityPage = jpaRepository.searchByKeyword(keyword, pageable);
        return mapToPageResult(entityPage);
    }

    @Override
    public PageResult<Quote> searchQuotes(String keyword, Long customerId, Integer statusId, String quoteNumber, java.time.LocalDateTime fromDate, java.time.LocalDateTime toDate, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<QuoteEntity> entityPage = jpaRepository.searchQuotes(keyword, customerId, statusId, quoteNumber, fromDate, toDate, pageable);
        return mapToPageResult(entityPage);
    }

    private PageResult<Quote> mapToPageResult(Page<QuoteEntity> entityPage) {
        List<Quote> domainData = entityPage.getContent().stream()
                .map(QuoteJpaMapper::toDomain)
                .toList();

        return PageResult.<Quote>builder()
                .data(domainData)
                .currentPage(entityPage.getNumber())
                .pageSize(entityPage.getSize())
                .totalElements(entityPage.getTotalElements())
                .totalPages(entityPage.getTotalPages())
                .hasNext(entityPage.hasNext())
                .hasPrevious(entityPage.hasPrevious())
                .build();
    }

    @Override
    public void delete(Quote quote) {
        // 🔥 soft delete: chỉ save lại entity đã set deletedAt
        jpaRepository.save(QuoteJpaMapper.toEntity(quote));
    }

    @Override
    public boolean existsByQuoteNumber(String quoteNumber) {
        return jpaRepository.existsByQuoteNumberAndDeletedAtIsNull(quoteNumber);
    }

    @Override
    public boolean existsByQuoteNumberExcludeId(String quoteNumber, Integer excludeId) {
        return jpaRepository.existsByQuoteNumberAndIdNotAndDeletedAtIsNull(quoteNumber, excludeId);
    }

    @Override
    public long countByCustomerId(Long customerId) {
        return jpaRepository.countByCustomerIdAndDeletedAtIsNull(customerId);
    }

    @Override
    @org.springframework.transaction.annotation.Transactional
    public void updateTotalAmount(Integer id, java.math.BigDecimal totalAmount) {
        int rows = jpaRepository.updateTotalAmount(id, totalAmount);
        System.out.println("DEBUG updateTotalAmount: rows updated = " + rows + ", id=" + id + ", total=" + totalAmount);
    }

    @Override
    @org.springframework.transaction.annotation.Transactional
    public void updateQuoteNumber(Integer id, String quoteNumber) {
        jpaRepository.updateQuoteNumber(id, quoteNumber);
    }
}
