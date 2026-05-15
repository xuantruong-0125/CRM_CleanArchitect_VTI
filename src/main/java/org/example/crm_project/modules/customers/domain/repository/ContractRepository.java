package org.example.crm_project.modules.customers.domain.repository;

import org.example.crm_project.modules.customers.domain.entity.Contract;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Repository Interface: ContractRepository
 */
public interface ContractRepository {
    Contract save(Contract contract);
    Optional<Contract> findById(Long id);
    Optional<Contract> findByContractCode(String contractCode);
    Page<Contract> findByCustomerId(Long customerId, Pageable pageable);
    Page<Contract> findByStatus(String status, Pageable pageable);
    void delete(Long id);
    boolean existsById(Long id);
    long count();
}
