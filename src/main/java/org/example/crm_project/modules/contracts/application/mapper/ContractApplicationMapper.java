package org.example.crm_project.modules.contracts.application.mapper;

import org.example.crm_project.modules.contracts.application.dto.request.CreateContractRequest;
import org.example.crm_project.modules.contracts.application.dto.request.UpdateContractRequest;
import org.example.crm_project.modules.contracts.application.dto.response.ContractResponse;
import org.example.crm_project.modules.contracts.domain.constant.ContractStatus;
import org.example.crm_project.modules.contracts.domain.entity.Contract;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ContractApplicationMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", constant = "DRAFT")
    @Mapping(target = "customerName", ignore = true)
    @Mapping(target = "ownerName", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    Contract toDomain(CreateContractRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "customerName", ignore = true)
    @Mapping(target = "ownerName", ignore = true)
    @Mapping(target = "quoteId", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    void updateDomainFromRequest(@MappingTarget Contract contract, UpdateContractRequest request);

    @Mapping(target = "statusDisplayName", expression = "java(contract.getStatus() != null ? contract.getStatus().getDisplayName() : null)")
    ContractResponse toResponse(Contract contract);
}
