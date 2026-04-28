package org.example.crm_project.modules.contacts_managerment.infrastructure.persistence.mapper;

import org.example.crm_project.modules.contacts_managerment.domain.entity.Contact;
import org.example.crm_project.modules.contacts_managerment.infrastructure.persistence.entity.ContactEntity;
import org.springframework.stereotype.Component;

@Component
public class ContactPersistenceMapper {

    public ContactEntity toEntity(Contact contact) {
        if (contact == null) return null;
        return ContactEntity.builder()
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
    }

    public Contact toDomain(ContactEntity entity) {
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
