package org.example.crm_project.modules.customers_managerment.infrastructure.persistence.jpa;

import org.example.crm_project.modules.customers_managerment.infrastructure.persistence.entity.FeedbackEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * JPA Repository: FeedbackJpaRepository
 */
@Repository
public interface FeedbackJpaRepository extends JpaRepository<FeedbackEntity, Long> {
    Page<FeedbackEntity> findByCustomerId(Long customerId, Pageable pageable);
    Page<FeedbackEntity> findByStatus(String status, Pageable pageable);
    Page<FeedbackEntity> findByPriority(String priority, Pageable pageable);
}
