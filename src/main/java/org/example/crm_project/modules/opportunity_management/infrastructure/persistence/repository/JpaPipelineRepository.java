package org.example.crm_project.modules.opportunity_management.infrastructure.persistence.repository;

import org.example.crm_project.modules.opportunity_management.infrastructure.persistence.entity.PipelineJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA interface – Pipeline.
 */
public interface JpaPipelineRepository extends JpaRepository<PipelineJpaEntity, Integer> {
}
