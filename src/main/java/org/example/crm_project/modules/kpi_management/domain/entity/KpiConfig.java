package org.example.crm_project.modules.kpi_management.domain.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class KpiConfig {
    private Integer id;
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private String status;
    private String description;
    private Integer createdBy;
    private Integer updatedBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
    private List<KpiTarget> targets;
    private List<KpiAssignment> assignments;

    public KpiConfig() {
    }

    public KpiConfig(Integer id, String name, LocalDate startDate, LocalDate endDate, String status, String description,
            Integer createdBy, Integer updatedBy, LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deletedAt,
            List<KpiTarget> targets, List<KpiAssignment> assignments) {
        this.id = id;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.description = description;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
        this.targets = targets;
        this.assignments = assignments;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public Integer getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Integer updatedBy) {
        this.updatedBy = updatedBy;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }

    public List<KpiTarget> getTargets() {
        return targets;
    }

    public void setTargets(List<KpiTarget> targets) {
        this.targets = targets;
    }

    public List<KpiAssignment> getAssignments() {
        return assignments;
    }

    public void setAssignments(List<KpiAssignment> assignments) {
        this.assignments = assignments;
    }

    // ===== Business Logic =====
    public void update(String name, LocalDate startDate, LocalDate endDate, String status, String description,
            Integer updatedBy, List<KpiTarget> targets, List<KpiAssignment> assignments) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.description = description;
        this.updatedBy = updatedBy;
        this.targets = targets;
        this.assignments = assignments;
        this.updatedAt = LocalDateTime.now();
    }

    public void softDelete() {
        this.deletedAt = LocalDateTime.now();
    }
}
