package org.example.crm_project.modules.quote_management.application.dto.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

/**
 * DTO trả về thông tin một dòng sản phẩm trong báo giá.
 */
@Data
@Builder
public class QuoteLineItemResponse {

    private Integer id;
    private Integer quoteId;
    private Integer productId;
    private String productName;
    private Integer quantity;
    private BigDecimal unitPrice;
    private BigDecimal discountValue;
    private BigDecimal lineTotal;
}
