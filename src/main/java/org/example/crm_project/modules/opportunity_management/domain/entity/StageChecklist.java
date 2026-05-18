package org.example.crm_project.modules.opportunity_management.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Domain Entity – StageChecklist (danh sách công việc cần thực hiện trong stage).
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StageChecklist {
    private Integer id;
    private String taskName;
    private String description;
    private Boolean isMandatory;
    private Integer sortOrder;

    // Tham chiếu Stage
    private Integer stageId;
    private String stageName;
}
