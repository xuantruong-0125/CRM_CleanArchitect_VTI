package org.example.crm_project.modules.products_managerment.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
@Entity
@Table(name = "product_prices")
public class PriceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;
    @Column(name = "base_price")
    BigDecimal basePrice;
    @Column(name = "tax_rate")
    BigDecimal taxRate;
    @Column(name = "effective_from")
    LocalDateTime effectiveFrom;
    @Column(name = "effective_to")
    LocalDateTime effectiveTo;
    @Column(name = "final_price")
    BigDecimal finalPrice;
    @Column(name = "created_at")
    LocalDateTime createdAt;
    @Column(name = "updated_at")
    LocalDateTime updatedAt;
    @Column(name = "deleted_at")
    LocalDateTime deletedAt;
    @Column(name = "created_by")
    Long createdBy;
    @Column(name = "updated_by")
    Long updatedBy;
    @Column(name = "is_active")
    Boolean isActive;

    @ManyToOne
    @JoinColumn(name = "product_id")
    ProductEntity productEntity;
}
