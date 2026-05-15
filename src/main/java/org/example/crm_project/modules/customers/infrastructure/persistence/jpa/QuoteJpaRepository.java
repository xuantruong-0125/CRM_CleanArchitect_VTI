package org.example.crm_project.modules.customers.infrastructure.persistence.jpa;

import org.example.crm_project.modules.customers.infrastructure.persistence.entity.QuoteEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * JPA Repository: QuoteJpaRepository
 */
@Repository
public interface QuoteJpaRepository extends JpaRepository<QuoteEntity, Long> {
    Optional<QuoteEntity> findByQuoteCode(String quoteCode);
    Page<QuoteEntity> findByCustomerId(Long customerId, Pageable pageable);
    Page<QuoteEntity> findByStatus(String status, Pageable pageable);
}
