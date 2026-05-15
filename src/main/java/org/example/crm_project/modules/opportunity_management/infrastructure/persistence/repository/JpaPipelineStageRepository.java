package org.example.crm_project.modules.opportunity_management.infrastructure.persistence.repository;

import org.example.crm_project.modules.opportunity_management.infrastructure.persistence.entity.PipelineStageJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Spring Data JPA interface – PipelineStage.
 */
public interface JpaPipelineStageRepository extends JpaRepository<PipelineStageJpaEntity, Integer> {
    List<PipelineStageJpaEntity> findByPipelineId(Integer pipelineId);
}
