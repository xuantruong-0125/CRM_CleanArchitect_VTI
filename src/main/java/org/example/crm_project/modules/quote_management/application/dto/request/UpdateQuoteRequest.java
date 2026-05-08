package org.example.crm_project.modules.quote_management.application.dto.request;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * DTO nhận từ client khi cập nhật báo giá.
 */
@Data
public class UpdateQuoteRequest {

    private String quoteNumber;

    private Integer customerId;

    private Integer opportunityId;

    private Integer statusId;

    private String currencyCode;

    private BigDecimal exchangeRate;

    private LocalDate validUntil;

    private Integer templateId;

    private Integer updatedBy;

    private List<QuoteLineItemRequest> lineItems = new ArrayList<>();
}
