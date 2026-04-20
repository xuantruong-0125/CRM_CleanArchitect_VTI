package org.example.crm_project.modules.quote_management.infrastructure.persistence.mapper;

import org.example.crm_project.modules.quote_management.domain.entity.Quote;
import org.example.crm_project.modules.quote_management.infrastructure.persistence.entity.QuoteEntity;

import java.math.BigDecimal;

/**
 * JPA Mapper: chuyển đổi giữa QuoteEntity (infrastructure) và Quote (domain).
 */
public class QuoteJpaMapper {

    // ===== DOMAIN → ENTITY (để lưu vào DB) =====
    public static QuoteEntity toEntity(Quote quote) {
        QuoteEntity e = new QuoteEntity();
        e.setId(quote.getId()); // 🔥 quan trọng khi update
        e.setQuoteNumber(quote.getQuoteNumber());
        e.setCustomerId(quote.getCustomerId());
        e.setOpportunityId(quote.getOpportunityId());
        e.setStatusId(quote.getStatusId());
        e.setTotalAmount(quote.getTotalAmount() != null ? quote.getTotalAmount() : BigDecimal.ZERO);
        e.setCurrencyCode(quote.getCurrencyCode());
        e.setExchangeRate(quote.getExchangeRate());
        e.setValidUntil(quote.getValidUntil());
        e.setTemplateId(quote.getTemplateId());
        e.setCreatedBy(quote.getCreatedBy());
        e.setUpdatedBy(quote.getUpdatedBy());
        e.setDeletedAt(quote.getDeletedAt()); // 🔥 quan trọng cho soft delete
        return e;
    }

    // ===== ENTITY → DOMAIN (load từ DB) =====
    public static Quote toDomain(QuoteEntity e) {
        return new Quote(
                e.getId(),
                e.getQuoteNumber(),
                e.getCustomerId(),
                e.getOpportunityId(),
                e.getStatusId(),
                e.getTotalAmount(),
                e.getCurrencyCode(),
                e.getExchangeRate(),
                e.getValidUntil(),
                e.getTemplateId(),
                e.getCreatedBy(),
                e.getUpdatedBy(),
                e.getCreatedAt(),
                e.getUpdatedAt(),
                e.getDeletedAt()
        );
    }
}
