package org.example.crm_project.modules.invoices.infrastructure.persistence.mapper;

import org.example.crm_project.modules.invoices.domain.entity.Invoice;
import org.example.crm_project.modules.invoices.domain.entity.InvoiceLineItem;
import org.example.crm_project.modules.invoices.infrastructure.persistence.entity.InvoiceJpaEntity;
import org.example.crm_project.modules.invoices.infrastructure.persistence.entity.InvoiceLineItemJpaEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class InvoicePersistenceMapper {
    public InvoiceJpaEntity toJpaEntity(Invoice domain) {
        if (domain == null) return null;
        InvoiceJpaEntity entity = new InvoiceJpaEntity();
        entity.setId(domain.getId());
        entity.setInvoiceNumber(domain.getInvoiceNumber());
        entity.setCustomerId(domain.getCustomerId());
        entity.setOrderId(domain.getOrderId());
        entity.setTemplateId(domain.getTemplateId());
        entity.setAssignedTo(domain.getAssignedTo());
        entity.setTotalAmount(domain.getTotalAmount());
        entity.setCurrencyCode(domain.getCurrencyCode());
        entity.setExchangeRate(domain.getExchangeRate());
        entity.setIssueDate(domain.getIssueDate());
        entity.setDueDate(domain.getDueDate());
        entity.setStatus(domain.getStatus());

        if (domain.getLineItems() != null) {
            domain.getLineItems().forEach(item -> {
                InvoiceLineItemJpaEntity itemEntity = new InvoiceLineItemJpaEntity();
                itemEntity.setId(item.getId());
                itemEntity.setProductId(item.getProductId());
                itemEntity.setQuantity(item.getQuantity());
                itemEntity.setUnitPrice(item.getUnitPrice());
                entity.addLineItem(itemEntity);
            });
        }
        return entity;
    }

    public Invoice toDomainEntity(InvoiceJpaEntity entity) {
        if (entity == null) return null;
        List<InvoiceLineItem> domainItems = entity.getLineItems().stream()
                .map(item -> InvoiceLineItem.builder()
                        .id(item.getId())
                        .productId(item.getProductId())
                        .quantity(item.getQuantity())
                        .unitPrice(item.getUnitPrice())
                        .build())
                .collect(Collectors.toList());

        return Invoice.builder()
                .id(entity.getId())
                .invoiceNumber(entity.getInvoiceNumber())
                .customerId(entity.getCustomerId())
                .orderId(entity.getOrderId())
                .templateId(entity.getTemplateId())
                .assignedTo(entity.getAssignedTo())
                .totalAmount(entity.getTotalAmount())
                .currencyCode(entity.getCurrencyCode())
                .exchangeRate(entity.getExchangeRate())
                .issueDate(entity.getIssueDate())
                .dueDate(entity.getDueDate())
                .status(entity.getStatus())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .lineItems(domainItems)
                .build();
    }
}