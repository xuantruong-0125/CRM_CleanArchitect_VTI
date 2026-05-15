package org.example.crm_project.modules.customers.infrastructure.repository;

import org.example.crm_project.modules.customers.domain.entity.Quote;
import org.example.crm_project.modules.customers.domain.repository.QuoteRepository;
import org.example.crm_project.modules.customers.infrastructure.persistence.entity.QuoteEntity;
import org.example.crm_project.modules.customers.infrastructure.persistence.jpa.QuoteJpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Repository Implementation: QuoteRepositoryImpl
 */
@Repository
public class QuoteRepositoryImpl implements QuoteRepository {

    private final QuoteJpaRepository jpaRepository;

    public QuoteRepositoryImpl(QuoteJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Quote save(Quote quote) {
        QuoteEntity entity = domainToEntity(quote);
        QuoteEntity saved = jpaRepository.save(entity);
        return entityToDomain(saved);
    }

    @Override
    public Optional<Quote> findById(Long id) {
        return jpaRepository.findById(id).map(this::entityToDomain);
    }

    @Override
    public Optional<Quote> findByQuoteCode(String quoteCode) {
        return jpaRepository.findByQuoteCode(quoteCode).map(this::entityToDomain);
    }

    @Override
    public Page<Quote> findByCustomerId(Long customerId, Pageable pageable) {
        Page<QuoteEntity> page = jpaRepository.findByCustomerId(customerId, pageable);
        return page.map(this::entityToDomain);
    }

    @Override
    public Page<Quote> findByStatus(String status, Pageable pageable) {
        Page<QuoteEntity> page = jpaRepository.findByStatus(status, pageable);
        return page.map(this::entityToDomain);
    }

    @Override
    public void delete(Long id) {
        Optional<QuoteEntity> entity = jpaRepository.findById(id);
        entity.ifPresent(e -> {
            e.setDeletedAt(LocalDateTime.now());
            jpaRepository.save(e);
        });
    }

    @Override
    public boolean existsById(Long id) {
        return jpaRepository.existsById(id);
    }

    @Override
    public long count() {
        return jpaRepository.count();
    }

    private Quote entityToDomain(QuoteEntity entity) {
        if (entity == null) return null;

        Quote quote = new Quote();
        quote.setId(entity.getId());
        quote.setCustomerId(entity.getCustomerId());
        quote.setQuoteCode(entity.getQuoteCode());
        quote.setQuoteName(entity.getQuoteName());
        quote.setQuoteDate(entity.getQuoteDate());
        quote.setValidUntil(entity.getValidUntil());
        quote.setSubtotalAmount(entity.getSubtotalAmount());
        quote.setDiscountAmount(entity.getDiscountAmount());
        quote.setTotalAmount(entity.getTotalAmount());
        quote.setStatus(entity.getStatus());
        quote.setNotes(entity.getNotes());
        quote.setTemplateId(entity.getTemplateId());
        quote.setCreatedBy(entity.getCreatedBy());
        quote.setUpdatedBy(entity.getUpdatedBy());
        quote.setCreatedAt(entity.getCreatedAt());
        quote.setUpdatedAt(entity.getUpdatedAt());
        quote.setDeletedAt(entity.getDeletedAt());

        return quote;
    }

    private QuoteEntity domainToEntity(Quote quote) {
        if (quote == null) return null;

        QuoteEntity entity = new QuoteEntity();
        entity.setId(quote.getId());
        entity.setCustomerId(quote.getCustomerId());
        entity.setQuoteCode(quote.getQuoteCode());
        entity.setQuoteName(quote.getQuoteName());
        entity.setQuoteDate(quote.getQuoteDate());
        entity.setValidUntil(quote.getValidUntil());
        entity.setSubtotalAmount(quote.getSubtotalAmount());
        entity.setDiscountAmount(quote.getDiscountAmount());
        entity.setTotalAmount(quote.getTotalAmount());
        entity.setStatus(quote.getStatus());
        entity.setNotes(quote.getNotes());
        entity.setTemplateId(quote.getTemplateId());
        entity.setCreatedBy(quote.getCreatedBy());
        entity.setUpdatedBy(quote.getUpdatedBy());
        entity.setCreatedAt(quote.getCreatedAt());
        entity.setUpdatedAt(quote.getUpdatedAt());
        entity.setDeletedAt(quote.getDeletedAt());

        return entity;
    }
}
