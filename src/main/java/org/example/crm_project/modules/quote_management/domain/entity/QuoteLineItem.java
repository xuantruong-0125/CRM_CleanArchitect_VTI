package org.example.crm_project.modules.quote_management.domain.entity;

import java.math.BigDecimal;

/**
 * Domain entity cho dòng sản phẩm trong báo giá.
 * POJO thuần túy - không có JPA annotation.
 */
public class QuoteLineItem {

    private Integer id;
    private Integer quoteId;
    private Integer productId;
    private Integer quantity;
    private BigDecimal unitPrice;
    private BigDecimal discountValue;
    private BigDecimal lineTotal;

    // ===== Constructor tạo mới =====
    public QuoteLineItem(Integer productId, Integer quantity,
                         BigDecimal unitPrice, BigDecimal discountValue) {
        validateLineItem(productId, quantity, unitPrice);
        this.productId = productId;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.discountValue = discountValue != null ? discountValue : BigDecimal.ZERO;
    }

    // ===== Constructor load từ DB =====
    public QuoteLineItem(Integer id, Integer quoteId, Integer productId,
                         Integer quantity, BigDecimal unitPrice,
                         BigDecimal discountValue, BigDecimal lineTotal) {
        this.id = id;
        this.quoteId = quoteId;
        this.productId = productId;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.discountValue = discountValue != null ? discountValue : BigDecimal.ZERO;
        this.lineTotal = lineTotal;
    }

    // ===== Getter =====
    public Integer getId() { return id; }
    public Integer getQuoteId() { return quoteId; }
    public Integer getProductId() { return productId; }
    public Integer getQuantity() { return quantity; }
    public BigDecimal getUnitPrice() { return unitPrice; }
    public BigDecimal getDiscountValue() { return discountValue; }
    public BigDecimal getLineTotal() { return lineTotal; }

    // ===== Setter (dùng khi set quoteId sau khi tạo quote) =====
    public void assignToQuote(Integer quoteId) {
        if (quoteId == null) {
            throw new IllegalArgumentException("Quote ID cannot be null");
        }
        this.quoteId = quoteId;
    }

    // ===== Validation =====
    private void validateLineItem(Integer productId, Integer quantity, BigDecimal unitPrice) {
        if (productId == null) {
            throw new IllegalArgumentException("Product ID cannot be null");
        }
        if (quantity == null || quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than 0");
        }
        if (unitPrice == null || unitPrice.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Unit price cannot be negative");
        }
    }
}
