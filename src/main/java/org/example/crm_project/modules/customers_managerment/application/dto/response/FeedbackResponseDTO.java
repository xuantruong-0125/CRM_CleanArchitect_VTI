package org.example.crm_project.modules.customers_managerment.application.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * DTO: FeedbackResponseDTO
 * Response DTO for Feedback entity
 */
@Data
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
}
