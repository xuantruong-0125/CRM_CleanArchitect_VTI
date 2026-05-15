package org.example.crm_project.modules.customers.infrastructure.persistence.jpa;

import org.example.crm_project.modules.customers.infrastructure.persistence.entity.InvoiceEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * JPA Repository: InvoiceJpaRepository
 */
@Repository
public interface InvoiceJpaRepository extends JpaRepository<InvoiceEntity, Long> {
    Optional<InvoiceEntity> findByInvoiceCode(String invoiceCode);
    Page<InvoiceEntity> findByCustomerId(Long customerId, Pageable pageable);
    Page<InvoiceEntity> findByStatus(String status, Pageable pageable);
}
