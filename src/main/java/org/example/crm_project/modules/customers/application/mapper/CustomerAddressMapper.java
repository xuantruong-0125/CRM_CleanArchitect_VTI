package org.example.crm_project.modules.customers.application.mapper;

import org.example.crm_project.modules.customers.application.dto.request.CreateCustomerAddressDTO;
import org.example.crm_project.modules.customers.application.dto.response.CustomerAddressResponseDTO;
import org.example.crm_project.modules.customers.domain.entity.CustomerAddress;
import org.springframework.stereotype.Component;

/**
 * Mapper: CustomerAddressMapper
 * DTO ↔ Domain entity conversion
 */
@Component
public class CustomerAddressMapper {

    public CustomerAddress toEntity(CreateCustomerAddressDTO createDTO) {
        if (createDTO == null) return null;

        CustomerAddress customerAddress = new CustomerAddress();
        customerAddress.setCustomerId(createDTO.getCustomerId());
        customerAddress.setAddressType(createDTO.getAddressType());
        customerAddress.setFullAddress(createDTO.getFullAddress());
        customerAddress.setProvinceId(createDTO.getProvinceId());
        customerAddress.setIsPrimary(createDTO.getIsPrimary());

        return customerAddress;
    }

    public CustomerAddressResponseDTO toResponseDTO(CustomerAddress customerAddress) {
        if (customerAddress == null) return null;

        CustomerAddressResponseDTO dto = new CustomerAddressResponseDTO();
        dto.setId(customerAddress.getId());
        dto.setCustomerId(customerAddress.getCustomerId());
        dto.setAddressType(customerAddress.getAddressType());
        dto.setFullAddress(customerAddress.getFullAddress());
        dto.setProvinceId(customerAddress.getProvinceId());
        dto.setIsPrimary(customerAddress.getIsPrimary());
        dto.setCreatedAt(customerAddress.getCreatedAt());
        dto.setUpdatedAt(customerAddress.getUpdatedAt());

        return dto;
    }

    public void updateEntityFromDTO(CustomerAddress customerAddress, CreateCustomerAddressDTO createDTO) {
        if (customerAddress == null || createDTO == null) return;

        customerAddress.setCustomerId(createDTO.getCustomerId());
        customerAddress.setAddressType(createDTO.getAddressType());
        customerAddress.setFullAddress(createDTO.getFullAddress());
        customerAddress.setProvinceId(createDTO.getProvinceId());
        customerAddress.setIsPrimary(createDTO.getIsPrimary());
    }
}
