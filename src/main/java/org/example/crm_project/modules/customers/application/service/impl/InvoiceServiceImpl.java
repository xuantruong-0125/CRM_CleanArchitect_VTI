package org.example.crm_project.modules.customers.application.service.impl;

import org.example.crm_project.modules.customers.application.dto.request.CreateInvoiceDTO;
import org.example.crm_project.modules.customers.application.dto.response.InvoiceResponseDTO;
import org.example.crm_project.modules.customers.application.mapper.InvoiceMapper;
import org.example.crm_project.modules.customers.application.service.InvoiceService;
import org.example.crm_project.modules.customers.domain.entity.Invoice;
import org.example.crm_project.modules.customers.domain.repository.InvoiceRepository;
import org.example.crm_project.modules.customers.domain.exception.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation: InvoiceServiceImpl
 * Business logic for Invoice management
 */
@Service
@Transactional
public class InvoiceServiceImpl implements InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final InvoiceMapper invoiceMapper;

    public InvoiceServiceImpl(InvoiceRepository invoiceRepository, InvoiceMapper invoiceMapper) {
        this.invoiceRepository = invoiceRepository;
        this.invoiceMapper = invoiceMapper;
    }

    @Override
    public InvoiceResponseDTO createInvoice(CreateInvoiceDTO createDTO) {
        Invoice invoice = invoiceMapper.toEntity(createDTO);
        Invoice saved = invoiceRepository.save(invoice);
        return invoiceMapper.toResponseDTO(saved);
    }

    @Override
    public InvoiceResponseDTO getInvoiceById(Long id) {
        Invoice invoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Hóa đơn không tìm thấy: " + id));
        return invoiceMapper.toResponseDTO(invoice);
    }

    @Override
    public Optional<InvoiceResponseDTO> getInvoiceByCode(String invoiceCode) {
        return invoiceRepository.findByInvoiceCode(invoiceCode)
                .map(invoiceMapper::toResponseDTO);
    }

    @Override
    public Page<InvoiceResponseDTO> getInvoicesByCustomer(Long customerId, Pageable pageable) {
        Page<Invoice> invoices = invoiceRepository.findByCustomerId(customerId, pageable);
        return invoices.map(invoiceMapper::toResponseDTO);
    }

    @Override
    public Page<InvoiceResponseDTO> getInvoicesByStatus(String status, Pageable pageable) {
        Page<Invoice> invoices = invoiceRepository.findByStatus(status, pageable);
        return invoices.map(invoiceMapper::toResponseDTO);
    }

    @Override
    public InvoiceResponseDTO updateInvoice(Long id, CreateInvoiceDTO createDTO) {
        Invoice invoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Hóa đơn không tìm thấy: " + id));
        invoiceMapper.updateEntityFromDTO(invoice, createDTO);
        Invoice updated = invoiceRepository.save(invoice);
        return invoiceMapper.toResponseDTO(updated);
    }

    @Override
    public void deleteInvoice(Long id) {
        if (!invoiceRepository.existsById(id)) {
            throw new EntityNotFoundException("Hóa đơn không tìm thấy: " + id);
        }
        invoiceRepository.delete(id);
    }

    @Override
    @Transactional(readOnly = true)
    public long countInvoices() {
        return invoiceRepository.count();
    }
}
