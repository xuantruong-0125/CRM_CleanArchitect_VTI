package org.example.crm_project.modules.products_managerment.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PriceResponse {
    private Long id;
    private BigDecimal basePrice;
    private BigDecimal taxRate;
    private LocalDateTime effectiveFrom;
    private LocalDateTime effectiveTo;
    private BigDecimal finalPrice;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Boolean isActive;
    private Long productId;
    private String productName;
    private String productSkuCode;
    private String productImageUrl;
}
