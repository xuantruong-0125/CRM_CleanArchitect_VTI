package org.example.crm_project.modules.opportunity_management.infrastructure.persistence.mapper;

import org.example.crm_project.modules.opportunity_management.domain.entity.PipelineStage;
import org.example.crm_project.modules.opportunity_management.infrastructure.persistence.entity.PipelineJpaEntity;
import org.example.crm_project.modules.opportunity_management.infrastructure.persistence.entity.PipelineStageJpaEntity;
import org.springframework.stereotype.Component;

/**
 * Infrastructure Mapper: PipelineStage JPA Entity ↔ Domain Model.
 */
@Component
public class PipelineStageEntityMapper {

    public PipelineStage toDomain(PipelineStageJpaEntity entity) {
        if (entity == null) return null;
        return PipelineStage.builder()
                .id(entity.getId())
                .stageName(entity.getStageName())
                .probability(entity.getProbability())
                .maxDaysAllowed(entity.getMaxDaysAllowed())
                .sortOrder(entity.getSortOrder())
                .pipelineId(entity.getPipeline() != null ? entity.getPipeline().getId() : null)
                .pipelineName(entity.getPipeline() != null ? entity.getPipeline().getName() : null)
                .build();
    }

    public PipelineStageJpaEntity toEntity(PipelineStage domain) {
        if (domain == null) return null;
        PipelineStageJpaEntity entity = new PipelineStageJpaEntity();
        entity.setId(domain.getId());
        entity.setStageName(domain.getStageName());
        entity.setProbability(domain.getProbability());
        entity.setMaxDaysAllowed(domain.getMaxDaysAllowed());
        entity.setSortOrder(domain.getSortOrder());

        if (domain.getPipelineId() != null) {
            PipelineJpaEntity pipeline = new PipelineJpaEntity();
            pipeline.setId(domain.getPipelineId());
            entity.setPipeline(pipeline);
        }
        return entity;
    }
}
