package org.example.crm_project.modules.invoices.infrastructure.persistence.repository;

import org.example.crm_project.modules.invoices.infrastructure.persistence.entity.InvoiceJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceJpaRepository extends JpaRepository<InvoiceJpaEntity, Long>, JpaSpecificationExecutor<InvoiceJpaEntity> {
}