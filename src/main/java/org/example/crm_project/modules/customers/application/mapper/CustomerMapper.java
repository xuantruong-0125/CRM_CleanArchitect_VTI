package org.example.crm_project.modules.customers.application.mapper;

import org.example.crm_project.modules.customers.application.dto.request.CreateCustomerDTO;
import org.example.crm_project.modules.customers.application.dto.response.CustomerResponseDTO;
import org.example.crm_project.modules.customers.application.dto.request.UpdateCustomerDTO;
import org.example.crm_project.modules.customers.domain.constant.CustomerStatus;
import org.example.crm_project.modules.customers.domain.constant.CustomerTier;
import org.example.crm_project.modules.customers.domain.constant.CustomerType;
import org.example.crm_project.modules.customers.domain.entity.Customer;
import org.springframework.stereotype.Component;

/**
 * Mapper: CustomerMapper
 * Chuyển đổi giữa Customer entity và DTO
 */
@Component
public class CustomerMapper {

    /**
     * Chuyển CreateCustomerDTO thành Customer entity
     */
    public Customer toEntity(CreateCustomerDTO createDTO) {
        if (createDTO == null) {
            return null;
        }

        Customer customer = new Customer();
        customer.setName(createDTO.getName());
        customer.setType(CustomerType.valueOf(createDTO.getType()));
        customer.setPhone(createDTO.getPhone());
        customer.setEmail(createDTO.getEmail());
        customer.setTaxCode(createDTO.getTaxCode());
        customer.setShortName(createDTO.getShortName());
        customer.setFax(createDTO.getFax());
        customer.setDescription(createDTO.getDescription());
        customer.setEstablishedDate(createDTO.getEstablishedDate());
        customer.setSourceId(createDTO.getSourceId());
        customer.setAssignedTo(createDTO.getAssignedTo());

        // Set default status and tier
        if (createDTO.getStatusId() != null) {
            customer.setStatus(CustomerStatus.fromId(createDTO.getStatusId()));
        } else {
            customer.setStatus(CustomerStatus.ACTIVE);
        }

        if (createDTO.getTierId() != null) {
            customer.setTier(CustomerTier.fromId(createDTO.getTierId()));
        } else {
            customer.setTier(CustomerTier.SILVER);
        }

        return customer;
    }

    /**
     * Chuyển Customer entity thành CustomerResponseDTO
     */
    public CustomerResponseDTO toResponseDTO(Customer customer) {
        if (customer == null) {
            return null;
        }

        CustomerResponseDTO dto = new CustomerResponseDTO();
        dto.setId(customer.getId());
        dto.setCustomerCode(customer.getCustomerCode());
        dto.setType(customer.getType() != null ? customer.getType().getCode() : null);
        dto.setName(customer.getName());
        dto.setShortName(customer.getShortName());
        dto.setTaxCode(customer.getTaxCode());
        dto.setPhone(customer.getPhone());
        dto.setEmail(customer.getEmail());
        dto.setFax(customer.getFax());
        dto.setDescription(customer.getDescription());
        dto.setSourceId(customer.getSourceId());
        dto.setStatusName(customer.getStatus() != null ? customer.getStatus().getName() : null);
        dto.setTierName(customer.getTier() != null ? customer.getTier().getName() : null);
        dto.setAssignedTo(customer.getAssignedTo());
        dto.setCreatedAt(customer.getCreatedAt());
        dto.setUpdatedAt(customer.getUpdatedAt());

        return dto;
    }

    /**
     * Cập nhật Customer entity từ UpdateCustomerDTO
     */
    public Customer updateEntityFromDTO(UpdateCustomerDTO updateDTO, Customer customer) {
        if (updateDTO == null || customer == null) {
            return customer;
        }

        if (updateDTO.getName() != null) {
            customer.setName(updateDTO.getName());
        }
        if (updateDTO.getShortName() != null) {
            customer.setShortName(updateDTO.getShortName());
        }
        if (updateDTO.getPhone() != null) {
            customer.setPhone(updateDTO.getPhone());
        }
        if (updateDTO.getEmail() != null) {
            customer.setEmail(updateDTO.getEmail());
        }
        if (updateDTO.getFax() != null) {
            customer.setFax(updateDTO.getFax());
        }
        if (updateDTO.getDescription() != null) {
            customer.setDescription(updateDTO.getDescription());
        }
        if (updateDTO.getEstablishedDate() != null) {
            customer.setEstablishedDate(updateDTO.getEstablishedDate());
        }
        if (updateDTO.getSourceId() != null) {
            customer.setSourceId(updateDTO.getSourceId());
        }
        if (updateDTO.getStatusId() != null) {
            customer.setStatus(CustomerStatus.fromId(updateDTO.getStatusId()));
        }
        if (updateDTO.getTierId() != null) {
            customer.setTier(CustomerTier.fromId(updateDTO.getTierId()));
        }
        if (updateDTO.getAssignedTo() != null) {
            customer.setAssignedTo(updateDTO.getAssignedTo());
        }

        return customer;
    }
}
