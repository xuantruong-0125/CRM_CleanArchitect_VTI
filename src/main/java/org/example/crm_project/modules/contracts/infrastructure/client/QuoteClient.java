package org.example.crm_project.modules.contracts.infrastructure.client;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Anti-Corruption Layer: truy vấn thông tin báo giá từ module Quote.
 */
public interface QuoteClient {

    QuoteData getQuoteById(Long quoteId);

    @Getter
    @Setter
    class QuoteData {
        private Long id;
        private Long customerId;
        private BigDecimal totalAmount;
        private String currencyCode;
        private BigDecimal exchangeRate;
        private String status; // DRAFT, SENT, APPROVED, REJECTED

        public boolean isApproved() {
            return "APPROVED".equalsIgnoreCase(status);
        }
    }
}
