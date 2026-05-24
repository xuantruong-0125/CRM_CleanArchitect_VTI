package org.example.crm_project.modules.orders.infrastructure.persistence.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "order_line_items")
@Data
public class OrderLineItemJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private OrderJpaEntity order;

    @Column(name = "product_id")
    private Long productId;

    private Integer quantity;

    @Column(name = "unit_price")
    private BigDecimal unitPrice;

    @Column(name = "total_price", insertable = false, updatable = false)
    private BigDecimal totalPrice;
}