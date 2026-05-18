package org.example.crm_project.modules.opportunity_management.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO nhận dữ liệu PipelineStage từ form.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PipelineStageRequest {
    private Integer id;
    private String stageName;
    private Integer maxDaysAllowed;
    private Integer sortOrder;
    private Integer pipelineId;
}
