package org.example.crm_project.modules.customers.infrastructure.repository;

import org.example.crm_project.modules.customers.domain.entity.Invoice;
import org.example.crm_project.modules.customers.domain.repository.InvoiceRepository;
import org.example.crm_project.modules.customers.infrastructure.persistence.entity.InvoiceEntity;
import org.example.crm_project.modules.customers.infrastructure.persistence.jpa.InvoiceJpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Repository Implementation: InvoiceRepositoryImpl
 */
@Repository
public class InvoiceRepositoryImpl implements InvoiceRepository {

    private final InvoiceJpaRepository jpaRepository;

    public InvoiceRepositoryImpl(InvoiceJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Invoice save(Invoice invoice) {
        InvoiceEntity entity = domainToEntity(invoice);
        InvoiceEntity saved = jpaRepository.save(entity);
        return entityToDomain(saved);
    }

    @Override
    public Optional<Invoice> findById(Long id) {
        return jpaRepository.findById(id).map(this::entityToDomain);
    }

    @Override
    public Optional<Invoice> findByInvoiceCode(String invoiceCode) {
        return jpaRepository.findByInvoiceCode(invoiceCode).map(this::entityToDomain);
    }

    @Override
    public Page<Invoice> findByCustomerId(Long customerId, Pageable pageable) {
        Page<InvoiceEntity> page = jpaRepository.findByCustomerId(customerId, pageable);
        return page.map(this::entityToDomain);
    }

    @Override
    public Page<Invoice> findByStatus(String status, Pageable pageable) {
        Page<InvoiceEntity> page = jpaRepository.findByStatus(status, pageable);
        return page.map(this::entityToDomain);
    }

    @Override
    public void delete(Long id) {
        Optional<InvoiceEntity> entity = jpaRepository.findById(id);
        entity.ifPresent(e -> {
            e.setDeletedAt(LocalDateTime.now());
            jpaRepository.save(e);
        });
    }

    @Override
    public boolean existsById(Long id) {
        return jpaRepository.existsById(id);
    }

    @Override
    public long count() {
        return jpaRepository.count();
    }

    private Invoice entityToDomain(InvoiceEntity entity) {
        if (entity == null) return null;

        Invoice invoice = new Invoice();
        invoice.setId(entity.getId());
        invoice.setCustomerId(entity.getCustomerId());
        invoice.setInvoiceCode(entity.getInvoiceCode());
        invoice.setInvoiceName(entity.getInvoiceName());
        invoice.setInvoiceDate(entity.getInvoiceDate());
        invoice.setDueDate(entity.getDueDate());
        invoice.setSubtotalAmount(entity.getSubtotalAmount());
        invoice.setTaxAmount(entity.getTaxAmount());
        invoice.setTotalAmount(entity.getTotalAmount());
        invoice.setPaidAmount(entity.getPaidAmount());
        invoice.setStatus(entity.getStatus());
        invoice.setPaymentMethod(entity.getPaymentMethod());
        invoice.setNotes(entity.getNotes());
        invoice.setTemplateId(entity.getTemplateId());
        invoice.setCreatedBy(entity.getCreatedBy());
        invoice.setUpdatedBy(entity.getUpdatedBy());
        invoice.setCreatedAt(entity.getCreatedAt());
        invoice.setUpdatedAt(entity.getUpdatedAt());
        invoice.setDeletedAt(entity.getDeletedAt());

        return invoice;
    }

    private InvoiceEntity domainToEntity(Invoice invoice) {
        if (invoice == null) return null;

        InvoiceEntity entity = new InvoiceEntity();
        entity.setId(invoice.getId());
        entity.setCustomerId(invoice.getCustomerId());
        entity.setInvoiceCode(invoice.getInvoiceCode());
        entity.setInvoiceName(invoice.getInvoiceName());
        entity.setInvoiceDate(invoice.getInvoiceDate());
        entity.setDueDate(invoice.getDueDate());
        entity.setSubtotalAmount(invoice.getSubtotalAmount());
        entity.setTaxAmount(invoice.getTaxAmount());
        entity.setTotalAmount(invoice.getTotalAmount());
        entity.setPaidAmount(invoice.getPaidAmount());
        entity.setStatus(invoice.getStatus());
        entity.setPaymentMethod(invoice.getPaymentMethod());
        entity.setNotes(invoice.getNotes());
        entity.setTemplateId(invoice.getTemplateId());
        entity.setCreatedBy(invoice.getCreatedBy());
        entity.setUpdatedBy(invoice.getUpdatedBy());
        entity.setCreatedAt(invoice.getCreatedAt());
        entity.setUpdatedAt(invoice.getUpdatedAt());
        entity.setDeletedAt(invoice.getDeletedAt());

        return entity;
    }
}
