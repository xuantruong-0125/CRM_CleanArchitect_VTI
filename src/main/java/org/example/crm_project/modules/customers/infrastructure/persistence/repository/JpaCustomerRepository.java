package org.example.crm_project.modules.customers.infrastructure.persistence.repository;

import org.example.crm_project.modules.customers.infrastructure.persistence.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JpaCustomerRepository extends JpaRepository<CustomerEntity, Long> {
    Optional<CustomerEntity> findByCustomerCode(String customerCode);

    List<CustomerEntity> findByDeletedAtIsNull();

    @org.springframework.data.jpa.repository.Query("SELECT c FROM CustomerEntity c WHERE (c.name LIKE %:keyword% OR c.customerCode LIKE %:keyword%) AND c.deletedAt IS NULL")
    List<CustomerEntity> searchTop10(@org.springframework.data.repository.query.Param("keyword") String keyword, org.springframework.data.domain.Pageable pageable);
}
