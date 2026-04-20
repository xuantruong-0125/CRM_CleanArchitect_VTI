package org.example.crm_project.modules.quote_management.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * JPA Entity cho bảng quote_line_items.
 */
@Entity
@Table(name = "quote_line_items")
@Getter
@Setter
public class QuoteLineItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "quote_id")
    private Integer quoteId;

    @Column(name = "product_id")
    private Integer productId;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "unit_price", precision = 15, scale = 2, nullable = false)
    private BigDecimal unitPrice;

    @Column(name = "discount_value", precision = 15, scale = 2)
    private BigDecimal discountValue;

    // Generated column - read only
    @Column(name = "line_total", insertable = false, updatable = false)
    private BigDecimal lineTotal;
}
