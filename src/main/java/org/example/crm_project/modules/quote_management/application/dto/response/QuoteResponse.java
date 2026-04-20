package org.example.crm_project.modules.quote_management.application.dto.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * DTO trả về thông tin tóm tắt báo giá (dùng cho danh sách).
 * Không bao gồm line items.
 */
@Data
@Builder
public class QuoteResponse {

    private Integer id;
    private String quoteNumber;
    private Integer customerId;
    private String customerName;
    private Integer statusId;
    private BigDecimal totalAmount;
    private String currencyCode;
    private LocalDate validUntil;
    private Integer templateId;
    private String templateName;
    private LocalDateTime createdAt;
}
