package org.example.crm_project.modules.contacts_managerment.infrastructure.persistence.mapper;

import org.example.crm_project.modules.contacts_managerment.domain.entity.Contact;
import org.example.crm_project.modules.contacts_managerment.domain.entity.Customer;
import org.example.crm_project.modules.contacts_managerment.infrastructure.persistence.entity.ContactEntity;
import org.example.crm_project.modules.contacts_managerment.infrastructure.persistence.entity.CustomerEntity;
import org.springframework.stereotype.Component;

import static java.util.stream.Collectors.toList;

@Component
public class CustomerPersistenceMapper {

    public CustomerEntity toEntity(Customer domain) {
        if (domain == null) return null;
        return CustomerEntity.builder()
                .id(domain.getId())
                .parentId(domain.getParentId())
                .customerCode(domain.getCustomerCode())
                .type(domain.getType())
                .name(domain.getName())
                .shortName(domain.getShortName())
                .taxCode(domain.getTaxCode())
                .phone(domain.getPhone())
                .email(domain.getEmail())
                .fax(domain.getFax())
                .establishedDate(domain.getEstablishedDate())
                .description(domain.getDescription())
                .sourceId(domain.getSourceId())
                .statusId(domain.getStatusId())
                .tierId(domain.getTierId())
                .assignedTo(domain.getAssignedTo())
                .createdBy(domain.getCreatedBy())
                .updatedBy(domain.getUpdatedBy())
                .createdAt(domain.getCreatedAt())
                .updatedAt(domain.getUpdatedAt())
                .deletedAt(domain.getDeletedAt())
                .build();
    }

    public Customer toDomain(CustomerEntity entity) {
        if (entity == null) return null;
        Customer customer = new Customer(
                entity.getId(),
                entity.getParentId(),
                entity.getCustomerCode(),
                entity.getType(),
                entity.getName(),
                entity.getShortName(),
                entity.getTaxCode(),
                entity.getPhone(),
                entity.getEmail(),
                entity.getFax(),
                entity.getEstablishedDate(),
                entity.getDescription(),
                entity.getSourceId(),
                entity.getStatusId(),
                entity.getTierId(),
                entity.getAssignedTo(),
                entity.getCreatedBy(),
                entity.getUpdatedBy(),
                entity.getCreatedAt(),
                entity.getUpdatedAt(),
                entity.getDeletedAt()
        );
        
        if (entity.getContacts() != null) {
            customer.setContacts(entity.getContacts().stream()
                .map(this::mapContactToDomain)
                .collect(toList()));
        }
        
        return customer;
    }

    private Contact mapContactToDomain(ContactEntity entity) {
        if (entity == null) return null;
        return new Contact(
            entity.getId(),
            entity.getFullName(),
            entity.getPosition(),
            entity.getPhone(),
            entity.getEmail(),
            entity.getAddress(),
            entity.getDob(),
            entity.getNotes(),
            entity.isPrimary(),
            entity.getCreatedBy(),
            entity.getUpdatedBy(),
            entity.getCreatedAt(),
            entity.getUpdatedAt(),
            entity.isActive(),
            entity.getDeletedAt()
        );
    }
}
