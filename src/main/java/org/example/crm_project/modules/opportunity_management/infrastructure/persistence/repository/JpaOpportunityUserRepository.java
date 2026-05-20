package org.example.crm_project.modules.opportunity_management.infrastructure.persistence.repository;

import org.example.crm_project.modules.opportunity_management.infrastructure.persistence.entity.UserJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA interface – User.
 */
public interface JpaOpportunityUserRepository extends JpaRepository<UserJpaEntity, Integer> {
}
