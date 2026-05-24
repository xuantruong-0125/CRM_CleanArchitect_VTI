package org.example.crm_project.modules.invoices.application.dto.response;

import org.example.crm_project.modules.invoices.domain.constant.InvoiceStatus;
import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class InvoiceResponse {
    private Long id;
    private String invoiceNumber;
    private Long customerId;
    private Long orderId;
    private Long assignedTo;
    private BigDecimal totalAmount;
    private String currencyCode;
    private LocalDate issueDate;
    private LocalDate dueDate;
    private InvoiceStatus status;
    private LocalDateTime createdAt;
    private List<InvoiceLineItemResponse> lineItems;

    @Data
    @Builder
    public static class InvoiceLineItemResponse {
        private Long id;
        private Long productId;
        private Integer quantity;
        private BigDecimal unitPrice;
        private BigDecimal totalPrice;
    }
}