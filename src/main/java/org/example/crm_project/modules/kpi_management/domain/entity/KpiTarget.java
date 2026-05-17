package org.example.crm_project.modules.kpi_management.domain.entity;

import java.math.BigDecimal;

public class KpiTarget {
    private Integer id;
    private Integer kpiConfigId;
    private String metricType;
    private BigDecimal targetValue;

    public KpiTarget() {
    }

    public KpiTarget(Integer id, Integer kpiConfigId, String metricType, BigDecimal targetValue) {
        this.id = id;
        this.kpiConfigId = kpiConfigId;
        this.metricType = metricType;
        this.targetValue = targetValue;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getKpiConfigId() {
        return kpiConfigId;
    }

    public void setKpiConfigId(Integer kpiConfigId) {
        this.kpiConfigId = kpiConfigId;
    }

    public String getMetricType() {
        return metricType;
    }

    public void setMetricType(String metricType) {
        this.metricType = metricType;
    }

    public BigDecimal getTargetValue() {
        return targetValue;
    }

    public void setTargetValue(BigDecimal targetValue) {
        this.targetValue = targetValue;
    }
}
