package org.example.crm_project.modules.orders.application.dto.request;

import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Data
public class CreateOrderRequest {
    private Long customerId;
    private Long opportunityId;
    private String currencyCode;
    private List<ItemRequest> items;

    @Data
    public static class ItemRequest {
        private Long productId;
        private Integer quantity;
        private BigDecimal unitPrice;
    }
}