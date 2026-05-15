package org.example.crm_project.modules.customers.domain.repository;

import org.example.crm_project.modules.customers.domain.entity.Opportunity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Repository Interface: OpportunityRepository
 */
public interface OpportunityRepository {
    Opportunity save(Opportunity opportunity);
    Optional<Opportunity> findById(Long id);
    Page<Opportunity> findByCustomerId(Long customerId, Pageable pageable);
    Page<Opportunity> findByAssignedUserId(Long userId, Pageable pageable);
    Page<Opportunity> findByHealthStatus(String healthStatus, Pageable pageable);
    List<Opportunity> findAll();
    void delete(Long id);
    boolean existsById(Long id);
    long count();
}
