package org.example.crm_project.modules.contacts_managerment.application.mapper;

import org.example.crm_project.modules.contacts_managerment.application.dto.request.CreateCustomerRequest;
import org.example.crm_project.modules.contacts_managerment.application.dto.request.UpdateCustomerRequest;
import org.example.crm_project.modules.contacts_managerment.application.dto.response.CustomerResponse;
import org.example.crm_project.modules.contacts_managerment.domain.entity.Customer;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class CustomerMapper {

    public Customer toDomain(CreateCustomerRequest request) {
        if (request == null) return null;
        Customer customer = new Customer();
        customer.setParentId(request.getParentId());
        customer.setCustomerCode(request.getCustomerCode());
        customer.setType(request.getType());
        customer.setName(request.getName());
        customer.setShortName(request.getShortName());
        customer.setTaxCode(request.getTaxCode());
        customer.setPhone(request.getPhone());
        customer.setEmail(request.getEmail());
        customer.setFax(request.getFax());
        customer.setEstablishedDate(request.getEstablishedDate());
        customer.setDescription(request.getDescription());
        customer.setSourceId(request.getSourceId());
        customer.setStatusId(request.getStatusId());
        customer.setTierId(request.getTierId());
        customer.setAssignedTo(request.getAssignedTo());
        customer.setCreatedAt(LocalDateTime.now());
        return customer;
    }

    public void updateDomain(Customer customer, UpdateCustomerRequest request) {
        if (request == null) return;
        if (request.getParentId() != null) customer.setParentId(request.getParentId());
        if (request.getCustomerCode() != null) customer.setCustomerCode(request.getCustomerCode());
        if (request.getType() != null) customer.setType(request.getType());
        if (request.getName() != null) customer.setName(request.getName());
        if (request.getShortName() != null) customer.setShortName(request.getShortName());
        if (request.getTaxCode() != null) customer.setTaxCode(request.getTaxCode());
        if (request.getPhone() != null) customer.setPhone(request.getPhone());
        if (request.getEmail() != null) customer.setEmail(request.getEmail());
        if (request.getFax() != null) customer.setFax(request.getFax());
        if (request.getEstablishedDate() != null) customer.setEstablishedDate(request.getEstablishedDate());
        if (request.getDescription() != null) customer.setDescription(request.getDescription());
        if (request.getSourceId() != null) customer.setSourceId(request.getSourceId());
        if (request.getStatusId() != null) customer.setStatusId(request.getStatusId());
        if (request.getTierId() != null) customer.setTierId(request.getTierId());
        if (request.getAssignedTo() != null) customer.setAssignedTo(request.getAssignedTo());
        customer.setUpdatedAt(LocalDateTime.now());
    }

    public CustomerResponse toResponse(Customer customer) {
        if (customer == null) return null;
        return CustomerResponse.builder()
                .id(customer.getId())
                .parentId(customer.getParentId())
                .customerCode(customer.getCustomerCode())
                .type(customer.getType())
                .name(customer.getName())
                .shortName(customer.getShortName())
                .taxCode(customer.getTaxCode())
                .phone(customer.getPhone())
                .email(customer.getEmail())
                .fax(customer.getFax())
                .establishedDate(customer.getEstablishedDate())
                .description(customer.getDescription())
                .sourceId(customer.getSourceId())
                .statusId(customer.getStatusId())
                .tierId(customer.getTierId())
                .assignedTo(customer.getAssignedTo())
                .createdAt(customer.getCreatedAt())
                .updatedAt(customer.getUpdatedAt())
                .build();
    }
}
