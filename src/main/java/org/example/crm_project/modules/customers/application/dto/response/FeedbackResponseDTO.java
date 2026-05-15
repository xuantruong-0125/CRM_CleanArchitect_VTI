package org.example.crm_project.modules.customers.application.dto.response;

import java.time.LocalDateTime;

/**
 * DTO: FeedbackResponseDTO
 * Response DTO for Feedback entity
 */
public class FeedbackResponseDTO {
    private Long id;
    private Long customerId;
    private String subject;
    private String description;
    private String priority;
    private String status;
    private Long assignedTo;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public FeedbackResponseDTO() {}

    public FeedbackResponseDTO(Long id, Long customerId, String subject) {
        this.id = id;
        this.customerId = customerId;
        this.subject = subject;
    }

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getCustomerId() { return customerId; }
    public void setCustomerId(Long customerId) { this.customerId = customerId; }

    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getPriority() { return priority; }
    public void setPriority(String priority) { this.priority = priority; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Long getAssignedTo() { return assignedTo; }
    public void setAssignedTo(Long assignedTo) { this.assignedTo = assignedTo; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
