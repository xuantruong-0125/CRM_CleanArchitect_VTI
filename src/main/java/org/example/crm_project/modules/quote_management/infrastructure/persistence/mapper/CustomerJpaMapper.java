package org.example.crm_project.modules.quote_management.infrastructure.persistence.mapper;

import org.example.crm_project.modules.quote_management.domain.constant.CustomerType;
import org.example.crm_project.modules.quote_management.domain.entity.Customer;
import org.example.crm_project.modules.quote_management.infrastructure.persistence.entity.CustomerEntity;

/**
 * JPA Mapper: chuyển đổi giữa CustomerEntity (infrastructure) và Customer (domain).
 */
public class CustomerJpaMapper {

    public static Customer toDomain(CustomerEntity e) {
        return new Customer(
                e.getId(),
                e.getCustomerCode(),
                e.getName(),
                e.getShortName(),
                e.getType() != null ? CustomerType.valueOf(e.getType()) : null,
                e.getPhone(),
                e.getEmail()
        );
    }
}
