package org.example.crm_project.modules.quote_management.domain.repository;

import org.example.crm_project.modules.quote_management.domain.entity.QuoteLineItem;

import java.util.List;

/**
 * Domain repository interface cho QuoteLineItem.
 * Thuần Java - không phụ thuộc bất kỳ framework nào.
 * Implementation nằm ở infrastructure layer.
 */
public interface QuoteLineItemRepository {

    QuoteLineItem save(QuoteLineItem lineItem);

    List<QuoteLineItem> saveAll(List<QuoteLineItem> lineItems);

    List<QuoteLineItem> findByQuoteId(Integer quoteId);

    void deleteByQuoteId(Integer quoteId);
}
