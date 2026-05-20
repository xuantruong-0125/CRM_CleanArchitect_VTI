package org.example.crm_project.modules.opportunity_management.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO trả về StageChecklist cho Presentation layer.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StageChecklistResponse {
    private Integer id;
    private String taskName;
    private String description;
    private Boolean isMandatory;
    private Integer sortOrder;
    private Integer stageId;
    private String stageName;
}
