package org.example.crm_project.modules.opportunity_management.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO nhận dữ liệu LossReason từ form.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LossReasonRequest {
    private Integer id;
    private String name;
    private String description;
    private Boolean isActive;
}
