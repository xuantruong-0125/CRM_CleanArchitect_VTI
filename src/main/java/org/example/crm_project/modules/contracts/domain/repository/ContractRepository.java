package org.example.crm_project.modules.contracts.domain.repository;

import org.example.crm_project.modules.contracts.domain.entity.Contract;
import org.example.crm_project.modules.contracts.domain.entity.ContractFilter;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

/**
 * Port (interface) thuộc tầng Domain.
 * Infrastructure sẽ implement interface này (Hexagonal Architecture).
 */
public interface ContractRepository {

    Contract save(Contract contract);

    Optional<Contract> findById(Long id);

    Optional<Contract> findByContractNumber(String contractNumber);

    Page<Contract> findAll(ContractFilter filter);

    List<Contract> findAllByIds(List<Long> ids);

    void softDelete(Long id, Long deletedBy);

    boolean existsByContractNumber(String contractNumber);

    long countByOwnerId(Long ownerId);

    /**
     * Trả về số thứ tự tiếp theo cho prefix tháng hiện tại.
     * VD: prefix="HD-202604-" → tìm MAX("HD-202604-XXXX") → trả về XXXX+1
     */
    int getNextSequenceForPrefix(String prefix);
}