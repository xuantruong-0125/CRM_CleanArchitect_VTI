package org.example.crm_project.modules.invoices.infrastructure.persistence.repository;

import org.example.crm_project.modules.invoices.domain.constant.InvoiceStatus;
import org.example.crm_project.modules.invoices.infrastructure.persistence.entity.InvoiceJpaEntity;
import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.Predicate;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class InvoiceSpecification {
    public static Specification<InvoiceJpaEntity> filter(
            String invoiceNumber, LocalDate issueDate, InvoiceStatus status, Long assignedTo) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (invoiceNumber != null && !invoiceNumber.isEmpty()) {
                predicates.add(cb.like(root.get("invoiceNumber"), "%" + invoiceNumber + "%"));
            }
            if (issueDate != null) {
                predicates.add(cb.equal(root.get("issueDate"), issueDate));
            }
            if (status != null) {
                predicates.add(cb.equal(root.get("status"), status));
            }
            if (assignedTo != null) {
                predicates.add(cb.equal(root.get("assignedTo"), assignedTo));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}