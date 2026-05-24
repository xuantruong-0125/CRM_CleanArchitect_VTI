package org.example.crm_project.modules.orders.domain.entity;

import  org.example.crm_project.modules.orders.domain.constant.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    private Long id;
    private String orderNumber;
    private Long customerId;
    private Long opportunityId;
    private BigDecimal totalAmount;
    private String currencyCode;
    private BigDecimal exchangeRate;
    private OrderStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Builder.Default
    private List<OrderLineItem> lineItems = new ArrayList<>();

    public void calculateTotalAmount() {
        if (this.lineItems == null || this.lineItems.isEmpty()) {
            this.totalAmount = BigDecimal.ZERO;
            return;
        }
        this.totalAmount = this.lineItems.stream()
                .map(OrderLineItem::calculateTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}