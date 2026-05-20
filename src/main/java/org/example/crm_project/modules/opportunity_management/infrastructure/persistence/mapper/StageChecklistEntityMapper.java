package org.example.crm_project.modules.opportunity_management.infrastructure.persistence.mapper;


import org.example.crm_project.modules.opportunity_management.domain.entity.StageChecklist;
import org.example.crm_project.modules.opportunity_management.infrastructure.persistence.entity.PipelineStageJpaEntity;
import org.example.crm_project.modules.opportunity_management.infrastructure.persistence.entity.StageChecklistJpaEntity;
import org.springframework.stereotype.Component;

/**
 * Infrastructure Mapper: StageChecklist JPA Entity ↔ Domain Model.
 */
@Component
public class StageChecklistEntityMapper {

    public StageChecklist toDomain(StageChecklistJpaEntity entity) {
        if (entity == null) return null;
        return StageChecklist.builder()
                .id(entity.getId())
                .taskName(entity.getTaskName())
                .description(entity.getDescription())
                .isMandatory(entity.getIsMandatory())
                .sortOrder(entity.getSortOrder())
                .stageId(entity.getStage() != null ? entity.getStage().getId() : null)
                .stageName(entity.getStage() != null ? entity.getStage().getStageName() : null)
                .build();
    }

    public StageChecklistJpaEntity toEntity(StageChecklist domain) {
        if (domain == null) return null;
        StageChecklistJpaEntity entity = new StageChecklistJpaEntity();
        entity.setId(domain.getId());
        entity.setTaskName(domain.getTaskName());
        entity.setDescription(domain.getDescription());
        entity.setIsMandatory(domain.getIsMandatory());
        entity.setSortOrder(domain.getSortOrder());

        if (domain.getStageId() != null) {
            PipelineStageJpaEntity stage = new PipelineStageJpaEntity();
            stage.setId(domain.getStageId());
            entity.setStage(stage);
        }
        return entity;
    }
}
