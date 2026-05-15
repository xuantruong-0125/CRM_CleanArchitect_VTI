package org.example.crm_project.modules.customers.domain.entity;

import org.example.crm_project.modules.customers.domain.constant.ActivityType;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Domain Entity: Activity
 * Đại diện cho hoạt động với khách hàng (Email, gọi điện, gặp mặt, ...)
 */
public class Activity {
    private Long id;
    private ActivityType activityType;
    private String subject;
    private String description;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private LocalDateTime completedAt;
    private String outcome;
    private String relatedToType;  // LEAD, CUSTOMER, CONTACT
    private Long relatedToId;
    private Long performedBy;
    private Long createdBy;
    private Long updatedBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Integer status;
    private Boolean isImportant;
    private LocalDateTime deletedAt;

    // Constructor
    public Activity() {
    }

    public Activity(ActivityType activityType, String subject, String description) {
        this.activityType = activityType;
        this.subject = subject;
        this.description = description;
        this.createdAt = LocalDateTime.now();
        this.isImportant = false;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ActivityType getActivityType() {
        return activityType;
    }

    public void setActivityType(ActivityType activityType) {
        this.activityType = activityType;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(LocalDateTime completedAt) {
        this.completedAt = completedAt;
    }

    public String getOutcome() {
        return outcome;
    }

    public void setOutcome(String outcome) {
        this.outcome = outcome;
    }

    public String getRelatedToType() {
        return relatedToType;
    }

    public void setRelatedToType(String relatedToType) {
        this.relatedToType = relatedToType;
    }

    public Long getRelatedToId() {
        return relatedToId;
    }

    public void setRelatedToId(Long relatedToId) {
        this.relatedToId = relatedToId;
    }

    public Long getPerformedBy() {
        return performedBy;
    }

    public void setPerformedBy(Long performedBy) {
        this.performedBy = performedBy;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Long getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Long updatedBy) {
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Boolean getIsImportant() {
        return isImportant;
    }

    public void setIsImportant(Boolean important) {
        isImportant = important;
    }

    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }
}
