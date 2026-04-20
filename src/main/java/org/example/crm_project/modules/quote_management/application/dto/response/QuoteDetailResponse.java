package org.example.crm_project.modules.quote_management.application.dto.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO trả về chi tiết báo giá (bao gồm danh sách line items).
 */
@Data
@Builder
public class QuoteDetailResponse {

    private Integer id;
    private String quoteNumber;
    private Integer customerId;
    private String customerName;
    private Integer opportunityId;
    private Integer statusId;
    private BigDecimal totalAmount;
    private String currencyCode;
    private BigDecimal exchangeRate;
    private LocalDate validUntil;
    private Integer templateId;
    private String templateName;
    private Integer createdBy;
    private Integer updatedBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private List<QuoteLineItemResponse> lineItems;
}
