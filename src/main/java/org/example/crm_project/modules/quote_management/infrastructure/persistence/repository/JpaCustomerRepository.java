package org.example.crm_project.modules.quote_management.infrastructure.persistence.repository;

import org.example.crm_project.modules.quote_management.infrastructure.persistence.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data JPA repository cho CustomerEntity (read-only trong module báo giá).
 */
@Repository
public interface JpaCustomerRepository extends JpaRepository<CustomerEntity, Integer> {

    @Query("SELECT c FROM CustomerEntity c WHERE c.deletedAt IS NULL ORDER BY c.name ASC")
    List<CustomerEntity> findAllActive();
}
