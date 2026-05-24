package org.example.crm_project.modules.invoices.infrastructure.persistence.repository;

import org.example.crm_project.modules.invoices.infrastructure.persistence.entity.InvoiceProductViewEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceProductViewRepository extends JpaRepository<InvoiceProductViewEntity, Long> {
    // Kế thừa JpaRepository là đủ để sài hàm findAll() lấy sạch bảng sản phẩm
}