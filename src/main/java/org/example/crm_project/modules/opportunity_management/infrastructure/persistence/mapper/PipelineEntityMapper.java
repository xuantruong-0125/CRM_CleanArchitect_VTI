package org.example.crm_project.modules.opportunity_management.infrastructure.persistence.mapper;

import org.example.crm_project.modules.opportunity_management.domain.entity.Pipeline;
import org.example.crm_project.modules.opportunity_management.infrastructure.persistence.entity.PipelineJpaEntity;
import org.springframework.stereotype.Component;

/**
 * Infrastructure Mapper: Pipeline JPA Entity ↔ Domain Model.
 */
@Component
public class PipelineEntityMapper {

    public Pipeline toDomain(PipelineJpaEntity entity) {
        if (entity == null) return null;
        return Pipeline.builder()
                .id(entity.getId())
                .name(entity.getName())
                .build();
    }

    public PipelineJpaEntity toEntity(Pipeline domain) {
        if (domain == null) return null;
        PipelineJpaEntity entity = new PipelineJpaEntity();
        entity.setId(domain.getId());
        entity.setName(domain.getName());
        return entity;
    }
}
