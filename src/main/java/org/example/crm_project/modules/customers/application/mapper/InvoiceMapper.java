package org.example.crm_project.modules.customers.application.mapper;

import org.example.crm_project.modules.customers.application.dto.request.CreateInvoiceDTO;
import org.example.crm_project.modules.customers.application.dto.response.InvoiceResponseDTO;
import org.example.crm_project.modules.customers.domain.entity.Invoice;
import org.springframework.stereotype.Component;

/**
 * Mapper: InvoiceMapper
 * DTO ↔ Domain entity conversion
 */
@Component
public class InvoiceMapper {

    public Invoice toEntity(CreateInvoiceDTO createDTO) {
        if (createDTO == null) return null;

        Invoice invoice = new Invoice();
        invoice.setCustomerId(createDTO.getCustomerId());
        invoice.setInvoiceCode(createDTO.getInvoiceCode());
        invoice.setInvoiceName(createDTO.getInvoiceName());
        invoice.setInvoiceDate(createDTO.getInvoiceDate());
        invoice.setDueDate(createDTO.getDueDate());
        invoice.setSubtotalAmount(createDTO.getSubtotalAmount());
        invoice.setTaxAmount(createDTO.getTaxAmount());
        invoice.setTotalAmount(createDTO.getTotalAmount());
        invoice.setPaidAmount(createDTO.getPaidAmount());
        invoice.setStatus(createDTO.getStatus());
        invoice.setPaymentMethod(createDTO.getPaymentMethod());
        invoice.setNotes(createDTO.getNotes());
        invoice.setTemplateId(createDTO.getTemplateId());

        return invoice;
    }

    public InvoiceResponseDTO toResponseDTO(Invoice invoice) {
        if (invoice == null) return null;

        InvoiceResponseDTO dto = new InvoiceResponseDTO();
        dto.setId(invoice.getId());
        dto.setCustomerId(invoice.getCustomerId());
        dto.setInvoiceCode(invoice.getInvoiceCode());
        dto.setInvoiceName(invoice.getInvoiceName());
        dto.setInvoiceDate(invoice.getInvoiceDate());
        dto.setDueDate(invoice.getDueDate());
        dto.setSubtotalAmount(invoice.getSubtotalAmount());
        dto.setTaxAmount(invoice.getTaxAmount());
        dto.setTotalAmount(invoice.getTotalAmount());
        dto.setPaidAmount(invoice.getPaidAmount());
        dto.setStatus(invoice.getStatus());
        dto.setPaymentMethod(invoice.getPaymentMethod());
        dto.setCreatedAt(invoice.getCreatedAt());
        dto.setUpdatedAt(invoice.getUpdatedAt());

        return dto;
    }

    public void updateEntityFromDTO(Invoice invoice, CreateInvoiceDTO createDTO) {
        if (invoice == null || createDTO == null) return;

        invoice.setCustomerId(createDTO.getCustomerId());
        invoice.setInvoiceCode(createDTO.getInvoiceCode());
        invoice.setInvoiceName(createDTO.getInvoiceName());
        invoice.setInvoiceDate(createDTO.getInvoiceDate());
        invoice.setDueDate(createDTO.getDueDate());
        invoice.setSubtotalAmount(createDTO.getSubtotalAmount());
        invoice.setTaxAmount(createDTO.getTaxAmount());
        invoice.setTotalAmount(createDTO.getTotalAmount());
        invoice.setPaidAmount(createDTO.getPaidAmount());
        invoice.setStatus(createDTO.getStatus());
        invoice.setPaymentMethod(createDTO.getPaymentMethod());
        invoice.setNotes(createDTO.getNotes());
        invoice.setTemplateId(createDTO.getTemplateId());
    }
}
