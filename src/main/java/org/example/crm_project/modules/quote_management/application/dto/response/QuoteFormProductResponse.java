package org.example.crm_project.modules.quote_management.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuoteFormProductResponse {
    private Long id;
    private String name;
    private String skuCode;
    private BigDecimal finalPrice;
}
