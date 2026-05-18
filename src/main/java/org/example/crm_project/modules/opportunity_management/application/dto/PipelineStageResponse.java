package org.example.crm_project.modules.opportunity_management.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO trả về dữ liệu PipelineStage cho Presentation layer.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PipelineStageResponse {
    private Integer id;
    private String stageName;
    private Float probability;
    private Integer maxDaysAllowed;
    private Integer sortOrder;
    private Integer pipelineId;
    private String pipelineName;
}
