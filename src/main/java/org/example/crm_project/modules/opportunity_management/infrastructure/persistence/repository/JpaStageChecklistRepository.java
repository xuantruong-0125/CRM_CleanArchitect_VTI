package org.example.crm_project.modules.opportunity_management.infrastructure.persistence.repository;

import org.example.crm_project.modules.opportunity_management.infrastructure.persistence.entity.StageChecklistJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA interface – StageChecklist.
 */
public interface JpaStageChecklistRepository extends JpaRepository<StageChecklistJpaEntity, Integer> {
}
