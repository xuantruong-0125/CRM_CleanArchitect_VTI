package org.example.crm_project.modules.customers.infrastructure.persistence.mapper;

import org.example.crm_project.modules.customers.domain.constant.CustomerType;
import org.example.crm_project.modules.customers.domain.entity.Customer;
import org.example.crm_project.modules.customers.infrastructure.persistence.entity.CustomerEntity;
import org.springframework.stereotype.Component;

@Component
public class CustomerJpaMapper {

    public Customer toDomain(CustomerEntity entity) {
        if (entity == null) return null;
        return Customer.builder()
                .id(entity.getId())
                .customerCode(entity.getCustomerCode())
                .name(entity.getName())
                .shortName(entity.getShortName())
                .type(entity.getType() != null ? CustomerType.valueOf(entity.getType()) : null)
                .phone(entity.getPhone())
                .email(entity.getEmail())
                .build();
    }

    public CustomerEntity toEntity(Customer domain) {
        if (domain == null) return null;
        return CustomerEntity.builder()
                .id(domain.getId())
                .customerCode(domain.getCustomerCode())
                .name(domain.getName())
                .shortName(domain.getShortName())
                .type(domain.getType() != null ? domain.getType().name() : null)
                .phone(domain.getPhone())
                .email(domain.getEmail())
                .build();
    }
}
