package org.example.crm_project.modules.quote_management.infrastructure.repository;

import lombok.RequiredArgsConstructor;
import org.example.crm_project.modules.quote_management.domain.entity.Quote;
import org.example.crm_project.modules.quote_management.domain.repository.QuoteRepository;
import org.example.crm_project.modules.quote_management.infrastructure.persistence.mapper.QuoteJpaMapper;
import org.example.crm_project.modules.quote_management.infrastructure.persistence.repository.JpaQuoteRepository;
import org.springframework.stereotype.Repository;

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
                jpaRepository.save(QuoteJpaMapper.toEntity(quote))
        );
    }

    @Override
    public Optional<Quote> findById(Integer id) {
        return jpaRepository.findActiveById(id)
                .map(QuoteJpaMapper::toDomain);
    }

    @Override
    public List<Quote> findAllActive() {
        return jpaRepository.findAllActive()
                .stream()
                .map(QuoteJpaMapper::toDomain)
                .toList();
    }

    @Override
    public List<Quote> searchByKeyword(String keyword) {
        return jpaRepository.searchByKeyword(keyword)
                .stream()
                .map(QuoteJpaMapper::toDomain)
                .toList();
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
    public long countByCustomerId(Integer customerId) {
        return jpaRepository.countByCustomerIdAndDeletedAtIsNull(customerId);
    }
}
