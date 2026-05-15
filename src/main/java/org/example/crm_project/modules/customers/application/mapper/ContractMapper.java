package org.example.crm_project.modules.customers.application.mapper;

import org.example.crm_project.modules.customers.application.dto.request.CreateContractDTO;
import org.example.crm_project.modules.customers.application.dto.response.ContractResponseDTO;
import org.example.crm_project.modules.customers.domain.entity.Contract;
import org.springframework.stereotype.Component;

/**
 * Mapper: ContractMapper
 * DTO ↔ Domain entity conversion
 */
@Component
public class ContractMapper {

    public Contract toEntity(CreateContractDTO createDTO) {
        if (createDTO == null) return null;

        Contract contract = new Contract();
        contract.setCustomerId(createDTO.getCustomerId());
        contract.setContractCode(createDTO.getContractCode());
        contract.setContractName(createDTO.getContractName());
        contract.setStartDate(createDTO.getStartDate());
        contract.setEndDate(createDTO.getEndDate());
        contract.setTotalValue(createDTO.getTotalValue());
        contract.setStatus(createDTO.getStatus());
        contract.setTemplateId(createDTO.getTemplateId());

        return contract;
    }

    public ContractResponseDTO toResponseDTO(Contract contract) {
        if (contract == null) return null;

        ContractResponseDTO dto = new ContractResponseDTO();
        dto.setId(contract.getId());
        dto.setCustomerId(contract.getCustomerId());
        dto.setContractCode(contract.getContractCode());
        dto.setContractName(contract.getContractName());
        dto.setStartDate(contract.getStartDate());
        dto.setEndDate(contract.getEndDate());
        dto.setTotalValue(contract.getTotalValue());
        dto.setStatus(contract.getStatus());
        dto.setCreatedAt(contract.getCreatedAt());
        dto.setUpdatedAt(contract.getUpdatedAt());

        return dto;
    }

    public void updateEntityFromDTO(Contract contract, CreateContractDTO createDTO) {
        if (contract == null || createDTO == null) return;

        contract.setCustomerId(createDTO.getCustomerId());
        contract.setContractCode(createDTO.getContractCode());
        contract.setContractName(createDTO.getContractName());
        contract.setStartDate(createDTO.getStartDate());
        contract.setEndDate(createDTO.getEndDate());
        contract.setTotalValue(createDTO.getTotalValue());
        contract.setStatus(createDTO.getStatus());
        contract.setTemplateId(createDTO.getTemplateId());
    }
}
