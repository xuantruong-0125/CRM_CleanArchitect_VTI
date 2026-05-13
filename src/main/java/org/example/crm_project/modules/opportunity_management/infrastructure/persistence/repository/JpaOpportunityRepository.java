package org.example.crm_project.modules.opportunity_management.infrastructure.persistence.repository;

import org.example.crm_project.modules.opportunity_management.infrastructure.persistence.entity.OpportunityJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Spring Data JPA interface – Opportunity.
 * Chỉ nằm ở Infrastructure layer.
 */
public interface JpaOpportunityRepository extends JpaRepository<OpportunityJpaEntity, Integer>,
        JpaSpecificationExecutor<OpportunityJpaEntity> {
}
