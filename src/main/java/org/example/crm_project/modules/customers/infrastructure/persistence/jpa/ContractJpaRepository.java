package org.example.crm_project.modules.customers.infrastructure.persistence.jpa;

import org.example.crm_project.modules.customers.infrastructure.persistence.entity.ContractEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * JPA Repository: ContractJpaRepository
 */
@Repository
public interface ContractJpaRepository extends JpaRepository<ContractEntity, Long> {
    Optional<ContractEntity> findByContractCode(String contractCode);
    Page<ContractEntity> findByCustomerId(Long customerId, Pageable pageable);
    Page<ContractEntity> findByStatus(String status, Pageable pageable);
}
