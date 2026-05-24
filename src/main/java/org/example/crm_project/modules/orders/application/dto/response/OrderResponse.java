
package org.example.crm_project.modules.orders.application.dto.response;

import org.example.crm_project.modules.orders.domain.constant.OrderStatus;
import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class OrderResponse {
    private Long id;
    private String orderNumber;
    private Long customerId;
    private BigDecimal totalAmount;
    private String currencyCode;
    private OrderStatus status;
    private LocalDateTime createdAt;
    private List<OrderLineItemResponse> lineItems;

    @Data
    @Builder
    public static class OrderLineItemResponse {
        private Long id;
        private Long productId;
        private Integer quantity;
        private BigDecimal unitPrice;
        private BigDecimal totalPrice;
    }
}