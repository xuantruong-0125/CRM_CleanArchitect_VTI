package org.example.crm_project.modules.opportunity_management.application.mapper;
import org.example.crm_project.modules.opportunity_management.application.dto.StageChecklistRequest;
import org.example.crm_project.modules.opportunity_management.application.dto.StageChecklistResponse;
import org.example.crm_project.modules.opportunity_management.domain.entity.StageChecklist;
import org.springframework.stereotype.Component;

/**
 * Mapper StageChecklist: Domain ↔ DTO.
 */
@Component
public class StageChecklistMapper {

    public StageChecklist toDomain(StageChecklistRequest request) {
        return StageChecklist.builder()
                .id(request.getId())
                .taskName(request.getTaskName())
                .description(request.getDescription())
                .isMandatory(request.getIsMandatory())
                .sortOrder(request.getSortOrder())
                .stageId(request.getStageId())
                .build();
    }

    public StageChecklistResponse toResponse(StageChecklist checklist) {
        return StageChecklistResponse.builder()
                .id(checklist.getId())
                .taskName(checklist.getTaskName())
                .description(checklist.getDescription())
                .isMandatory(checklist.getIsMandatory())
                .sortOrder(checklist.getSortOrder())
                .stageId(checklist.getStageId())
                .stageName(checklist.getStageName())
                .build();
    }
}
