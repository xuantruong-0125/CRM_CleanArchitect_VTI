package org.example.crm_project.modules.opportunity_management.infrastructure.persistence.repository;

import org.example.crm_project.modules.opportunity_management.infrastructure.persistence.entity.LossReasonJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA interface – LossReason.
 */
public interface JpaLossReasonRepository extends JpaRepository<LossReasonJpaEntity, Integer> {
}
