package org.example.crm_project.modules.products_managerment.application.dto.request;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class UpdatePriceRequest {
    private BigDecimal basePrice;
    private BigDecimal taxRate;
    private LocalDateTime effectiveFrom;
    private LocalDateTime effectiveTo;
}
