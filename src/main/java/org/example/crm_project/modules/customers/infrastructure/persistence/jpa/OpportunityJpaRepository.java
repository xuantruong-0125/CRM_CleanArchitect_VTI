package org.example.crm_project.modules.customers.infrastructure.persistence.jpa;

import org.example.crm_project.modules.customers.infrastructure.persistence.entity.OpportunityEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * JPA Repository: OpportunityJpaRepository
 */
@Repository
public interface OpportunityJpaRepository extends JpaRepository<OpportunityEntity, Long> {
    Page<OpportunityEntity> findByCustomerId(Long customerId, Pageable pageable);
    Page<OpportunityEntity> findByAssignedUserId(Long userId, Pageable pageable);
    Page<OpportunityEntity> findByHealthStatus(String healthStatus, Pageable pageable);
}
