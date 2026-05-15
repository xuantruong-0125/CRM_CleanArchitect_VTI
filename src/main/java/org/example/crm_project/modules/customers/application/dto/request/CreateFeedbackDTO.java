package org.example.crm_project.modules.customers.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * DTO: CreateFeedbackDTO
 * Create/Update DTO for Feedback
 */
public class CreateFeedbackDTO {
    @NotNull(message = "ID khách hàng không được để trống")
    private Long customerId;

    @NotBlank(message = "Chủ đề phản hồi không được để trống")
    private String subject;

    private String description;
    private String priority;
    private String status;
    private Long assignedTo;

    public CreateFeedbackDTO() {}

    // Getters & Setters
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
}
