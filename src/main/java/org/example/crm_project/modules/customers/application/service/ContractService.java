package org.example.crm_project.modules.customers.application.service;

import org.example.crm_project.modules.customers.application.dto.request.CreateContractDTO;
import org.example.crm_project.modules.customers.application.dto.response.ContractResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ContractService {
    ContractResponseDTO createContract(CreateContractDTO createDTO);
    ContractResponseDTO getContractById(Long id);
    Optional<ContractResponseDTO> getContractByCode(String contractCode);
    Page<ContractResponseDTO> getContractsByCustomer(Long customerId, Pageable pageable);
    Page<ContractResponseDTO> getContractsByStatus(String status, Pageable pageable);
    ContractResponseDTO updateContract(Long id, CreateContractDTO createDTO);
    void deleteContract(Long id);
    long countContracts();
}
