package org.example.crm_project.modules.kpi_management.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "kpi_targets")
@Getter
@Setter
public class KpiTargetEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "kpi_config_id", nullable = false)
    private KpiConfigEntity kpiConfig;

    @Column(name = "metric_type")
    private String metricType;

    @Column(name = "target_value", precision = 15, scale = 2)
    private BigDecimal targetValue;
}
