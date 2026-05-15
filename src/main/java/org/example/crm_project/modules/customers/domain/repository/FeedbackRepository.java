package org.example.crm_project.modules.customers.domain.repository;

import org.example.crm_project.modules.customers.domain.entity.Feedback;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Repository Interface: FeedbackRepository
 */
public interface FeedbackRepository {
    Feedback save(Feedback feedback);
    Optional<Feedback> findById(Long id);
    Page<Feedback> findByCustomerId(Long customerId, Pageable pageable);
    Page<Feedback> findByStatus(String status, Pageable pageable);
    Page<Feedback> findByPriority(String priority, Pageable pageable);
    void delete(Long id);
    boolean existsById(Long id);
    long count();
}
