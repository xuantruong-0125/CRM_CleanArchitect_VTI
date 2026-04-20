package org.example.crm_project.modules.quote_management.application.dto.request;

import lombok.Data;

import java.math.BigDecimal;

/**
 * DTO nhận thông tin một dòng sản phẩm khi tạo/cập nhật báo giá.
 */
@Data
public class QuoteLineItemRequest {

    private Integer productId;

    private Integer quantity;

    private BigDecimal unitPrice;

    private BigDecimal discountValue;
}
