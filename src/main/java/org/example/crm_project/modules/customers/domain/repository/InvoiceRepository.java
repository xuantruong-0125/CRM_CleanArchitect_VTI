package org.example.crm_project.modules.customers.domain.repository;

import org.example.crm_project.modules.customers.domain.entity.Invoice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Repository Interface: InvoiceRepository
 */
public interface InvoiceRepository {
    Invoice save(Invoice invoice);
    Optional<Invoice> findById(Long id);
    Optional<Invoice> findByInvoiceCode(String invoiceCode);
    Page<Invoice> findByCustomerId(Long customerId, Pageable pageable);
    Page<Invoice> findByStatus(String status, Pageable pageable);
    void delete(Long id);
    boolean existsById(Long id);
    long count();
}
