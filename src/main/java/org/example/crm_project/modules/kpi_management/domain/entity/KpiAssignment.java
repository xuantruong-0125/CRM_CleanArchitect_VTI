package org.example.crm_project.modules.kpi_management.domain.entity;

import java.math.BigDecimal;

public class KpiAssignment {
    private Integer id;
    private Integer kpiConfigId;
    private Integer userId;
    private Integer organizationId;
    private BigDecimal commissionPercent;

    public KpiAssignment() {
    }

    public KpiAssignment(Integer id, Integer kpiConfigId, Integer userId, Integer organizationId, BigDecimal commissionPercent) {
        this.id = id;
        this.kpiConfigId = kpiConfigId;
        this.userId = userId;
        this.organizationId = organizationId;
        this.commissionPercent = commissionPercent;
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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Integer organizationId) {
        this.organizationId = organizationId;
    }

    public BigDecimal getCommissionPercent() {
        return commissionPercent;
    }

    public void setCommissionPercent(BigDecimal commissionPercent) {
        this.commissionPercent = commissionPercent;
    }
}
