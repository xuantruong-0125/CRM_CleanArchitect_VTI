package org.example.crm_project.modules.invoices.domain.repository;

import org.example.crm_project.modules.invoices.domain.entity.Invoice;
import java.util.Optional;

public interface InvoiceRepository {
    Invoice save(Invoice invoice);
    Optional<Invoice> findById(Long id);
    void deleteById(Long id);
}