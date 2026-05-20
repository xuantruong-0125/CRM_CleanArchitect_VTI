package org.example.crm_project.modules.opportunity_management.infrastructure.persistence.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * JPA Entity – Ánh xạ trực tiếp với bảng opportunities trong DB.
 * Chỉ nằm ở Infrastructure layer, Domain Model hoàn toàn tách biệt.
 */
@Data
@Entity
@Table(name = "opportunities")
public class OpportunityJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 200)
    private String name;

    @Column(name = "customer_id")
    private Integer customerId;

    @Column(name = "assigned_user_id")
    private Integer assignedUserId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", insertable = false, updatable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private CustomerJpaEntity customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assigned_user_id", insertable = false, updatable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private UserJpaEntity assignedUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pipeline_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private PipelineJpaEntity pipeline;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stage_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private PipelineStageJpaEntity stage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "loss_reason_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private LossReasonJpaEntity lossReason;

    @Column(name = "total_amount")
    private BigDecimal totalAmount;

    @Column(name = "deposit_amount")
    private BigDecimal depositAmount;

    @Column(name = "remaining_amount")
    private BigDecimal remainingAmount;

    @Column(name = "currency_code")
    private String currencyCode;

    @Column(name = "exchange_rate")
    private BigDecimal exchangeRate;

    @Column(name = "health_status")
    private String healthStatus;

    @Column(name = "expected_close_date")
    private LocalDate expectedCloseDate;
}
