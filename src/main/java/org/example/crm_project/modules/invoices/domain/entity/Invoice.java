package org.example.crm_project.modules.invoices.domain.entity;

import org.example.crm_project.modules.invoices.domain.constant.InvoiceStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Invoice {
    private Long id;
    private String invoiceNumber;
    private Long customerId;
    private Long orderId;
    private Long templateId;
    private Long assignedTo;
    private BigDecimal totalAmount;
    private String currencyCode;
    private BigDecimal exchangeRate;
    private LocalDate issueDate;
    private LocalDate dueDate;
    private InvoiceStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Builder.Default
    private List<InvoiceLineItem> lineItems = new ArrayList<>();

    public void calculateTotalAmount() {
        if (this.lineItems == null || this.lineItems.isEmpty()) {
            this.totalAmount = BigDecimal.ZERO;
            return;
        }
        this.totalAmount = this.lineItems.stream()
                .map(InvoiceLineItem::calculateTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}