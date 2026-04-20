package org.example.crm_project.modules.quote_management.infrastructure.repository;

import lombok.RequiredArgsConstructor;
import org.example.crm_project.modules.quote_management.domain.entity.QuoteLineItem;
import org.example.crm_project.modules.quote_management.domain.repository.QuoteLineItemRepository;
import org.example.crm_project.modules.quote_management.infrastructure.persistence.mapper.QuoteLineItemJpaMapper;
import org.example.crm_project.modules.quote_management.infrastructure.persistence.repository.JpaQuoteLineItemRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Implementation của QuoteLineItemRepository (domain interface).
 */
@Repository
@RequiredArgsConstructor
public class QuoteLineItemRepositoryImpl implements QuoteLineItemRepository {

    private final JpaQuoteLineItemRepository jpaRepository;

    @Override
    public QuoteLineItem save(QuoteLineItem lineItem) {
        return QuoteLineItemJpaMapper.toDomain(
                jpaRepository.save(QuoteLineItemJpaMapper.toEntity(lineItem))
        );
    }

    @Override
    public List<QuoteLineItem> saveAll(List<QuoteLineItem> lineItems) {
        var entities = lineItems.stream()
                .map(QuoteLineItemJpaMapper::toEntity)
                .toList();
        return jpaRepository.saveAll(entities)
                .stream()
                .map(QuoteLineItemJpaMapper::toDomain)
                .toList();
    }

    @Override
    public List<QuoteLineItem> findByQuoteId(Integer quoteId) {
        return jpaRepository.findByQuoteId(quoteId)
                .stream()
                .map(QuoteLineItemJpaMapper::toDomain)
                .toList();
    }

    @Override
    @Transactional
    public void deleteByQuoteId(Integer quoteId) {
        jpaRepository.deleteByQuoteId(quoteId);
    }
}
