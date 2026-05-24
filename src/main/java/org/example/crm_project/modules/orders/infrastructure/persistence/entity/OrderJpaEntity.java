package org.example.crm_project.modules.orders.infrastructure.persistence.entity;

import org.example.crm_project.modules.orders.domain.constant.OrderStatus;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
@SQLDelete(sql = "UPDATE orders SET deleted_at = CURRENT_TIMESTAMP WHERE id = ?")
@Where(clause = "deleted_at IS NULL")
public class OrderJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "order_number", unique = true, length = 50)
    private String orderNumber;

    @Column(name = "customer_id")
    private Long customerId;

    @Column(name = "opportunity_id")
    private Long opportunityId;

    @Column(name = "total_amount")
    private BigDecimal totalAmount;

    @Column(name = "currency_code", length = 10)
    private String currencyCode = "VND";

    @Column(name = "exchange_rate")
    private BigDecimal exchangeRate = new BigDecimal("1.0000");

    @Enumerated(EnumType.STRING)
    private OrderStatus status = OrderStatus.DRAFT;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderLineItemJpaEntity> lineItems = new ArrayList<>();

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    public void addLineItem(OrderLineItemJpaEntity item) {
        lineItems.add(item);
        item.setOrder(this);
    }
}