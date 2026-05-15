package org.example.crm_project.modules.customers.application.service.impl;

import org.example.crm_project.modules.customers.application.dto.request.CreateContractDTO;
import org.example.crm_project.modules.customers.application.dto.response.ContractResponseDTO;
import org.example.crm_project.modules.customers.application.mapper.ContractMapper;
import org.example.crm_project.modules.customers.application.service.ContractService;
import org.example.crm_project.modules.customers.domain.entity.Contract;
import org.example.crm_project.modules.customers.domain.repository.ContractRepository;
import org.example.crm_project.modules.customers.domain.exception.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation: ContractServiceImpl
 * Business logic for Contract management
 */
@Service
@Transactional
public class ContractServiceImpl implements ContractService {

    private final ContractRepository contractRepository;
    private final ContractMapper contractMapper;

    public ContractServiceImpl(ContractRepository contractRepository, ContractMapper contractMapper) {
        this.contractRepository = contractRepository;
        this.contractMapper = contractMapper;
    }

    @Override
    public ContractResponseDTO createContract(CreateContractDTO createDTO) {
        Contract contract = contractMapper.toEntity(createDTO);
        Contract saved = contractRepository.save(contract);
        return contractMapper.toResponseDTO(saved);
    }

    @Override
    public ContractResponseDTO getContractById(Long id) {
        Contract contract = contractRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Hợp đồng không tìm thấy: " + id));
        return contractMapper.toResponseDTO(contract);
    }

    @Override
    public Optional<ContractResponseDTO> getContractByCode(String contractCode) {
        return contractRepository.findByContractCode(contractCode)
                .map(contractMapper::toResponseDTO);
    }

    @Override
    public Page<ContractResponseDTO> getContractsByCustomer(Long customerId, Pageable pageable) {
        Page<Contract> contracts = contractRepository.findByCustomerId(customerId, pageable);
        return contracts.map(contractMapper::toResponseDTO);
    }

    @Override
    public Page<ContractResponseDTO> getContractsByStatus(String status, Pageable pageable) {
        Page<Contract> contracts = contractRepository.findByStatus(status, pageable);
        return contracts.map(contractMapper::toResponseDTO);
    }

    @Override
    public ContractResponseDTO updateContract(Long id, CreateContractDTO createDTO) {
        Contract contract = contractRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Hợp đồng không tìm thấy: " + id));
        contractMapper.updateEntityFromDTO(contract, createDTO);
        Contract updated = contractRepository.save(contract);
        return contractMapper.toResponseDTO(updated);
    }

    @Override
    public void deleteContract(Long id) {
        if (!contractRepository.existsById(id)) {
            throw new EntityNotFoundException("Hợp đồng không tìm thấy: " + id);
        }
        contractRepository.delete(id);
    }

    @Override
    @Transactional(readOnly = true)
    public long countContracts() {
        return contractRepository.count();
    }
}
