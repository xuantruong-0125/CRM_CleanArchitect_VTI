package org.example.crm_project.modules.quote_management.infrastructure.persistence.mapper;

import org.example.crm_project.modules.quote_management.domain.entity.QuoteLineItem;
import org.example.crm_project.modules.quote_management.infrastructure.persistence.entity.QuoteLineItemEntity;

import java.math.BigDecimal;

/**
 * JPA Mapper: chuyển đổi giữa QuoteLineItemEntity (infrastructure) và QuoteLineItem (domain).
 */
public class QuoteLineItemJpaMapper {

    // ===== DOMAIN → ENTITY =====
    public static QuoteLineItemEntity toEntity(QuoteLineItem item) {
        QuoteLineItemEntity e = new QuoteLineItemEntity();
        e.setId(item.getId());
        e.setQuoteId(item.getQuoteId());
        e.setProductId(item.getProductId());
        e.setQuantity(item.getQuantity());
        e.setUnitPrice(item.getUnitPrice());
        e.setDiscountValue(item.getDiscountValue() != null ? item.getDiscountValue() : BigDecimal.ZERO);
        return e;
    }

    // ===== ENTITY → DOMAIN =====
    public static QuoteLineItem toDomain(QuoteLineItemEntity e) {
        return new QuoteLineItem(
                e.getId(),
                e.getQuoteId(),
                e.getProductId(),
                e.getQuantity(),
                e.getUnitPrice(),
                e.getDiscountValue(),
                e.getLineTotal()
        );
    }
}
