package org.example.crm_project.modules.customers.infrastructure.repository;

import org.example.crm_project.modules.customers.domain.entity.Contract;
import org.example.crm_project.modules.customers.domain.repository.ContractRepository;
import org.example.crm_project.modules.customers.infrastructure.persistence.entity.ContractEntity;
import org.example.crm_project.modules.customers.infrastructure.persistence.jpa.ContractJpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Repository Implementation: ContractRepositoryImpl
 */
@Repository
public class ContractRepositoryImpl implements ContractRepository {

    private final ContractJpaRepository jpaRepository;

    public ContractRepositoryImpl(ContractJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Contract save(Contract contract) {
        ContractEntity entity = domainToEntity(contract);
        ContractEntity saved = jpaRepository.save(entity);
        return entityToDomain(saved);
    }

    @Override
    public Optional<Contract> findById(Long id) {
        return jpaRepository.findById(id).map(this::entityToDomain);
    }

    @Override
    public Optional<Contract> findByContractCode(String contractCode) {
        return jpaRepository.findByContractCode(contractCode).map(this::entityToDomain);
    }

    @Override
    public Page<Contract> findByCustomerId(Long customerId, Pageable pageable) {
        Page<ContractEntity> page = jpaRepository.findByCustomerId(customerId, pageable);
        return page.map(this::entityToDomain);
    }

    @Override
    public Page<Contract> findByStatus(String status, Pageable pageable) {
        Page<ContractEntity> page = jpaRepository.findByStatus(status, pageable);
        return page.map(this::entityToDomain);
    }

    @Override
    public void delete(Long id) {
        Optional<ContractEntity> entity = jpaRepository.findById(id);
        entity.ifPresent(e -> {
            e.setDeletedAt(LocalDateTime.now());
            jpaRepository.save(e);
        });
    }

    @Override
    public boolean existsById(Long id) {
        return jpaRepository.existsById(id);
    }

    @Override
    public long count() {
        return jpaRepository.count();
    }

    private Contract entityToDomain(ContractEntity entity) {
        if (entity == null) return null;

        Contract contract = new Contract();
        contract.setId(entity.getId());
        contract.setCustomerId(entity.getCustomerId());
        contract.setContractCode(entity.getContractCode());
        contract.setContractName(entity.getContractName());
        contract.setStartDate(entity.getStartDate());
        contract.setEndDate(entity.getEndDate());
        contract.setTotalValue(entity.getTotalValue());
        contract.setStatus(entity.getStatus());
        contract.setTemplateId(entity.getTemplateId());
        contract.setCreatedBy(entity.getCreatedBy());
        contract.setUpdatedBy(entity.getUpdatedBy());
        contract.setCreatedAt(entity.getCreatedAt());
        contract.setUpdatedAt(entity.getUpdatedAt());
        contract.setDeletedAt(entity.getDeletedAt());

        return contract;
    }

    private ContractEntity domainToEntity(Contract contract) {
        if (contract == null) return null;

        ContractEntity entity = new ContractEntity();
        entity.setId(contract.getId());
        entity.setCustomerId(contract.getCustomerId());
        entity.setContractCode(contract.getContractCode());
        entity.setContractName(contract.getContractName());
        entity.setStartDate(contract.getStartDate());
        entity.setEndDate(contract.getEndDate());
        entity.setTotalValue(contract.getTotalValue());
        entity.setStatus(contract.getStatus());
        entity.setTemplateId(contract.getTemplateId());
        entity.setCreatedBy(contract.getCreatedBy());
        entity.setUpdatedBy(contract.getUpdatedBy());
        entity.setCreatedAt(contract.getCreatedAt());
        entity.setUpdatedAt(contract.getUpdatedAt());
        entity.setDeletedAt(contract.getDeletedAt());

        return entity;
    }
}
