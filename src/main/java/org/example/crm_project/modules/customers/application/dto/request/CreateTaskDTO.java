package org.example.crm_project.modules.customers.application.dto.request;

import org.example.crm_project.modules.customers.domain.constant.TaskPriority;
import org.example.crm_project.modules.customers.domain.constant.TaskStatus;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class CreateTaskDTO {

    @NotBlank(message = "Tiêu đề không được để trống")
    private String subject;

    private String description;
    private LocalDate startDate;
    private LocalDate dueDate;
    private LocalDateTime completedAt;
    
    @NotNull(message = "Trạng thái không được để trống")
    private TaskStatus status;
    
    @NotNull(message = "Độ ưu tiên không được để trống")
    private TaskPriority priority;
    
    private Integer progressPercent;
    private String relatedToType;
    private Long relatedToId;
    private Long assignedTo;
    private Long assignedBy;
    private Long contactId;

    // Getters and Setters
    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    public LocalDate getDueDate() { return dueDate; }
    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }

    public LocalDateTime getCompletedAt() { return completedAt; }
    public void setCompletedAt(LocalDateTime completedAt) { this.completedAt = completedAt; }

    public TaskStatus getStatus() { return status; }
    public void setStatus(TaskStatus status) { this.status = status; }

    public TaskPriority getPriority() { return priority; }
    public void setPriority(TaskPriority priority) { this.priority = priority; }

    public Integer getProgressPercent() { return progressPercent; }
    public void setProgressPercent(Integer progressPercent) { this.progressPercent = progressPercent; }

    public String getRelatedToType() { return relatedToType; }
    public void setRelatedToType(String relatedToType) { this.relatedToType = relatedToType; }

    public Long getRelatedToId() { return relatedToId; }
    public void setRelatedToId(Long relatedToId) { this.relatedToId = relatedToId; }

    public Long getAssignedTo() { return assignedTo; }
    public void setAssignedTo(Long assignedTo) { this.assignedTo = assignedTo; }

    public Long getAssignedBy() { return assignedBy; }
    public void setAssignedBy(Long assignedBy) { this.assignedBy = assignedBy; }

    public Long getContactId() { return contactId; }
    public void setContactId(Long contactId) { this.contactId = contactId; }
}
