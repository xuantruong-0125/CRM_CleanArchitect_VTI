package org.example.crm_project.modules.contacts_managerment.infrastructure.persistence.mapper;

import org.example.crm_project.modules.contacts_managerment.domain.entity.Contact;
import org.example.crm_project.modules.contacts_managerment.infrastructure.persistence.entity.ContactEntity;
import org.example.crm_project.modules.customers.domain.entity.Customer;
import org.example.crm_project.modules.customers.infrastructure.persistence.entity.CustomerEntity;
import org.springframework.stereotype.Component;

@Component
public class ContactPersistenceMapper {

    public ContactEntity toEntity(Contact contact) {
        if (contact == null)
            return null;
        ContactEntity contactEntity = ContactEntity.builder()
                .id(contact.getId())
                .fullName(contact.getFullName())
                .position(contact.getPosition())
                .phone(contact.getPhone())
                .email(contact.getEmail())
                .address(contact.getAddress())
                .dob(contact.getDob())
                .notes(contact.getNotes())
                .isPrimary(contact.isPrimary())
                .createdBy(contact.getCreatedBy())
                .updatedBy(contact.getUpdatedBy())
                .createdAt(contact.getCreatedAt())
                .updatedAt(contact.getUpdatedAt())
                .isActive(contact.isActive())
                .deletedAt(contact.getDeletedAt())
                .build();

        if (contact.getCustomer() != null) {
            contactEntity.setCustomer(mapCustomerToEntity(contact.getCustomer()));
        }

        return contactEntity;
    }

    public Contact toDomain(ContactEntity entity) {
        if (entity == null)
            return null;
        Contact contact = new Contact(
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
                entity.getDeletedAt());
        if (entity.getCustomer() != null) {
            contact.setCustomer(mapCustomerToDomain(entity.getCustomer()));
        }
        return contact;
    }

    private CustomerEntity mapCustomerToEntity(Customer customer) {
        if (customer == null)
            return null;
        CustomerEntity entity = new CustomerEntity();
        entity.setId(customer.getId());
        entity.setName(customer.getName());
        // Map other fields if necessary, but usually ID is enough for relationships in
        // JPA
        return entity;
    }

    private Customer mapCustomerToDomain(CustomerEntity entity) {
        if (entity == null)
            return null;
        Customer customer = new Customer();
        customer.setId(entity.getId());
        customer.setName(entity.getName());
        // Map other fields if necessary
        return customer;
    }
}
