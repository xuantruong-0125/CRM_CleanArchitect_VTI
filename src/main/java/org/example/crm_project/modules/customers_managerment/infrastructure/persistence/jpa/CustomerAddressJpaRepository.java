package org.example.crm_project.modules.customers_managerment.infrastructure.persistence.jpa;

import org.example.crm_project.modules.customers_managerment.infrastructure.persistence.entity.CustomerAddressEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerAddressJpaRepository extends JpaRepository<CustomerAddressEntity, Long> {
    List<CustomerAddressEntity> findByCustomerId(Long customerId);
    Page<CustomerAddressEntity> findByCustomerId(Long customerId, Pageable pageable);
    
    @org.springframework.data.jpa.repository.Query("SELECT c FROM CustomerAddressEntity c WHERE c.customerId = :customerId AND c.isPrimary = true")
    Optional<CustomerAddressEntity> findPrimaryAddress(@org.springframework.data.repository.query.Param("customerId") Long customerId);
    
    void deleteByCustomerId(Long customerId);
}
