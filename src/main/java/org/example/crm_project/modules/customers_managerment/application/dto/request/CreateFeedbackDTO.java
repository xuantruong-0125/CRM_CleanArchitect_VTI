package org.example.crm_project.modules.customers_managerment.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * DTO: CreateFeedbackDTO
 * Create/Update DTO for Feedback
 */
@Data
public class CreateFeedbackDTO {
    @NotNull(message = "ID khách hàng không được để trống")
    private Long customerId;

    @NotBlank(message = "Chủ đề phản hồi không được để trống")
    private String subject;

    private String description;
    private String priority;
    private String status;
    private Long assignedTo;
}
