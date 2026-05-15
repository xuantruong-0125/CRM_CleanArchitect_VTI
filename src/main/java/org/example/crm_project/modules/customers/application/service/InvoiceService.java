package org.example.crm_project.modules.customers.application.service;

import org.example.crm_project.modules.customers.application.dto.request.CreateInvoiceDTO;
import org.example.crm_project.modules.customers.application.dto.response.InvoiceResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface InvoiceService {
    InvoiceResponseDTO createInvoice(CreateInvoiceDTO createDTO);
    InvoiceResponseDTO getInvoiceById(Long id);
    Optional<InvoiceResponseDTO> getInvoiceByCode(String invoiceCode);
    Page<InvoiceResponseDTO> getInvoicesByCustomer(Long customerId, Pageable pageable);
    Page<InvoiceResponseDTO> getInvoicesByStatus(String status, Pageable pageable);
    InvoiceResponseDTO updateInvoice(Long id, CreateInvoiceDTO createDTO);
    void deleteInvoice(Long id);
    long countInvoices();
}
