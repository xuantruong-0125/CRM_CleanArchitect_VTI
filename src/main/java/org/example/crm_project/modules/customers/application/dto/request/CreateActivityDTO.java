package org.example.crm_project.modules.customers.application.dto.request;

import org.example.crm_project.modules.customers.domain.constant.ActivityType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class CreateActivityDTO {

    @NotNull(message = "Loại hoạt động không được để trống")
    private ActivityType activityType;

    @NotBlank(message = "Tiêu đề không được để trống")
    private String subject;

    private String description;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private LocalDateTime completedAt;
    private String outcome;
    private String relatedToType;
    private Long relatedToId;
    private Long performedBy;
    private Boolean isImportant;
    private Integer status;

    // Getters and Setters
    public ActivityType getActivityType() { return activityType; }
    public void setActivityType(ActivityType activityType) { this.activityType = activityType; }

    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public LocalDateTime getStartDate() { return startDate; }
    public void setStartDate(LocalDateTime startDate) { this.startDate = startDate; }

    public LocalDateTime getEndDate() { return endDate; }
    public void setEndDate(LocalDateTime endDate) { this.endDate = endDate; }

    public LocalDateTime getCompletedAt() { return completedAt; }
    public void setCompletedAt(LocalDateTime completedAt) { this.completedAt = completedAt; }

    public String getOutcome() { return outcome; }
    public void setOutcome(String outcome) { this.outcome = outcome; }

    public String getRelatedToType() { return relatedToType; }
    public void setRelatedToType(String relatedToType) { this.relatedToType = relatedToType; }

    public Long getRelatedToId() { return relatedToId; }
    public void setRelatedToId(Long relatedToId) { this.relatedToId = relatedToId; }

    public Long getPerformedBy() { return performedBy; }
    public void setPerformedBy(Long performedBy) { this.performedBy = performedBy; }

    public Boolean getIsImportant() { return isImportant; }
    public void setIsImportant(Boolean isImportant) { this.isImportant = isImportant; }

    public Integer getStatus() { return status; }
    public void setStatus(Integer status) { this.status = status; }
}
