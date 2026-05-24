package org.example.crm_project.modules.invoices.application.dto.request;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
public class CreateInvoiceRequest {
    private String invoiceNumber;
    private Long customerId;
    private Long orderId;
    private String currencyCode;
    private LocalDate issueDate;
    private LocalDate dueDate;
    private List<ItemRequest> items;

    @Data
    public static class ItemRequest {
        private Long productId;
        private Integer quantity;
        private BigDecimal unitPrice;
    }
}