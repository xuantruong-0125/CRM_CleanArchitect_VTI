package org.example.crm_project.modules.opportunity_management.infrastructure.persistence.mapper;

import org.example.crm_project.modules.opportunity_management.domain.entity.Customer;
import org.example.crm_project.modules.opportunity_management.infrastructure.persistence.entity.CustomerJpaEntity;
import org.springframework.stereotype.Component;

/**
 * Infrastructure Mapper: Customer JPA Entity ↔ Domain Model.
 */
@Component
public class CustomerEntityMapper {

    public Customer toDomain(CustomerJpaEntity entity) {
        if (entity == null) return null;
        return Customer.builder()
                .id(entity.getId())
                .name(entity.getName())
                .shortName(entity.getShortName())
                .phone(entity.getPhone())
                .email(entity.getEmail())
                .build();
    }
}
