package org.example.crm_project.modules.quote_management.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * JPA Entity cho bảng quotes.
 * Chỉ dùng ở infrastructure layer, không ra ngoài domain.
 */
@Entity
@Table(name = "quotes")
@Getter
@Setter
public class QuoteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "quote_number", length = 50, unique = true)
    private String quoteNumber;

    @Column(name = "customer_id")
    private Integer customerId;

    @Column(name = "opportunity_id")
    private Integer opportunityId;

    @Column(name = "status_id")
    private Integer statusId;

    @Column(name = "total_amount", precision = 15, scale = 2)
    private BigDecimal totalAmount;

    @Column(name = "currency_code", length = 10)
    private String currencyCode;

    @Column(name = "exchange_rate", precision = 10, scale = 4)
    private BigDecimal exchangeRate;

    @Column(name = "valid_until")
    private LocalDate validUntil;

    @Column(name = "template_id")
    private Integer templateId;

    @Column(name = "created_by")
    private Integer createdBy;

    @Column(name = "updated_by")
    private Integer updatedBy;

    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", insertable = false, updatable = false)
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;
}
