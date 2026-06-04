package org.example.crm_project.modules.customers_managerment.infrastructure.persistence.jpa;

import org.example.crm_project.modules.customers_managerment.infrastructure.persistence.entity.FeedbackEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * JPA Repository: FeedbackJpaRepository
 */
@Repository
public interface FeedbackJpaRepository extends JpaRepository<FeedbackEntity, Long> {
    Optional<FeedbackEntity> findByIdAndDeletedAtIsNull(Long id);
    
    boolean existsByIdAndDeletedAtIsNull(Long id);
    
    Page<FeedbackEntity> findByCustomerIdAndDeletedAtIsNull(Long customerId, Pageable pageable);
    
    Page<FeedbackEntity> findByStatusAndDeletedAtIsNull(String status, Pageable pageable);
    
    Page<FeedbackEntity> findByPriorityAndDeletedAtIsNull(String priority, Pageable pageable);
    
    long countByDeletedAtIsNull();
}
