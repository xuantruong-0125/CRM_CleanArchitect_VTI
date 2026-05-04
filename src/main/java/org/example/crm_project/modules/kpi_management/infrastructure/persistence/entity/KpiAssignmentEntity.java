package org.example.crm_project.modules.kpi_management.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "target_assignments")
@Getter
@Setter
public class KpiAssignmentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "kpi_config_id", nullable = false)
    private KpiConfigEntity kpiConfig;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "organization_id")
    private Integer organizationId;

    @Column(name = "commission_percent", precision = 5, scale = 2)
    private BigDecimal commissionPercent;
}
