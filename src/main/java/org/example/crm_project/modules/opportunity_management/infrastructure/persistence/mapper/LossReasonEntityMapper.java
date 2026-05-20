package org.example.crm_project.modules.opportunity_management.infrastructure.persistence.mapper;

import org.example.crm_project.modules.opportunity_management.domain.entity.LossReason;
import org.example.crm_project.modules.opportunity_management.infrastructure.persistence.entity.LossReasonJpaEntity;
import org.springframework.stereotype.Component;

/**
 * Infrastructure Mapper: LossReason JPA Entity ↔ Domain Model.
 */
@Component
public class LossReasonEntityMapper {

    public LossReason toDomain(LossReasonJpaEntity entity) {
        if (entity == null) return null;
        return LossReason.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .isActive(entity.getIsActive())
                .build();
    }

    public LossReasonJpaEntity toEntity(LossReason domain) {
        if (domain == null) return null;
        LossReasonJpaEntity entity = new LossReasonJpaEntity();
        entity.setId(domain.getId());
        entity.setName(domain.getName());
        entity.setDescription(domain.getDescription());
        entity.setIsActive(domain.getIsActive());
        return entity;
    }
}
