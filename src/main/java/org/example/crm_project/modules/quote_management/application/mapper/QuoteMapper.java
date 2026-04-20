package org.example.crm_project.modules.quote_management.application.mapper;

import org.example.crm_project.modules.quote_management.application.dto.request.CreateQuoteRequest;
import org.example.crm_project.modules.quote_management.application.dto.request.QuoteLineItemRequest;
import org.example.crm_project.modules.quote_management.application.dto.response.QuoteDetailResponse;
import org.example.crm_project.modules.quote_management.application.dto.response.QuoteLineItemResponse;
import org.example.crm_project.modules.quote_management.application.dto.response.QuoteResponse;
import org.example.crm_project.modules.quote_management.domain.entity.Customer;
import org.example.crm_project.modules.quote_management.domain.entity.DocumentTemplate;
import org.example.crm_project.modules.quote_management.domain.entity.Quote;
import org.example.crm_project.modules.quote_management.domain.entity.QuoteLineItem;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Mapper chuyển đổi giữa Domain Entity và Application DTO.
 * Không phụ thuộc framework.
 */
public class QuoteMapper {

    // ===== REQUEST → DOMAIN ENTITY =====

    /**
     * Chuyển CreateQuoteRequest → Quote domain entity (tạo mới)
     */
    public static Quote toEntity(CreateQuoteRequest req) {
        return new Quote(
                req.getQuoteNumber(),
                req.getCustomerId(),
                req.getOpportunityId(),
                req.getStatusId(),
                req.getCurrencyCode(),
                req.getExchangeRate(),
                req.getValidUntil(),
                req.getTemplateId(),
                req.getCreatedBy()
        );
    }

    /**
     * Chuyển QuoteLineItemRequest → QuoteLineItem domain entity
     */
    public static QuoteLineItem toLineItemEntity(QuoteLineItemRequest req) {
        return new QuoteLineItem(
                req.getProductId(),
                req.getQuantity(),
                req.getUnitPrice(),
                req.getDiscountValue() != null ? req.getDiscountValue() : BigDecimal.ZERO
        );
    }

    // ===== DOMAIN ENTITY → RESPONSE =====

    /**
     * Chuyển Quote domain → QuoteResponse (dùng cho danh sách)
     * customerName và templateName được truyền vào từ service (đã resolve từ repository)
     */
    public static QuoteResponse toResponse(Quote quote, String customerName, String templateName) {
        return QuoteResponse.builder()
                .id(quote.getId())
                .quoteNumber(quote.getQuoteNumber())
                .customerId(quote.getCustomerId())
                .customerName(customerName)
                .statusId(quote.getStatusId())
                .totalAmount(quote.getTotalAmount())
                .currencyCode(quote.getCurrencyCode())
                .validUntil(quote.getValidUntil())
                .templateId(quote.getTemplateId())
                .templateName(templateName)
                .createdAt(quote.getCreatedAt())
                .build();
    }

    /**
     * Chuyển Quote domain + lineItems → QuoteDetailResponse (dùng cho chi tiết)
     */
    public static QuoteDetailResponse toDetailResponse(Quote quote,
                                                        String customerName,
                                                        String templateName,
                                                        List<QuoteLineItemResponse> lineItems) {
        return QuoteDetailResponse.builder()
                .id(quote.getId())
                .quoteNumber(quote.getQuoteNumber())
                .customerId(quote.getCustomerId())
                .customerName(customerName)
                .opportunityId(quote.getOpportunityId())
                .statusId(quote.getStatusId())
                .totalAmount(quote.getTotalAmount())
                .currencyCode(quote.getCurrencyCode())
                .exchangeRate(quote.getExchangeRate())
                .validUntil(quote.getValidUntil())
                .templateId(quote.getTemplateId())
                .templateName(templateName)
                .createdBy(quote.getCreatedBy())
                .updatedBy(quote.getUpdatedBy())
                .createdAt(quote.getCreatedAt())
                .updatedAt(quote.getUpdatedAt())
                .lineItems(lineItems)
                .build();
    }

    /**
     * Chuyển QuoteLineItem domain → QuoteLineItemResponse
     * productName được truyền vào từ service (đã resolve từ repository)
     */
    public static QuoteLineItemResponse toLineItemResponse(QuoteLineItem item, String productName) {
        return QuoteLineItemResponse.builder()
                .id(item.getId())
                .quoteId(item.getQuoteId())
                .productId(item.getProductId())
                .productName(productName)
                .quantity(item.getQuantity())
                .unitPrice(item.getUnitPrice())
                .discountValue(item.getDiscountValue())
                .lineTotal(item.getLineTotal())
                .build();
    }
}
