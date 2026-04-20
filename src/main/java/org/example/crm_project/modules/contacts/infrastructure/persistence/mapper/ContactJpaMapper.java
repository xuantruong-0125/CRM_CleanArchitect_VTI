package org.example.crm_project.modules.contacts.infrastructure.persistence.mapper;

import org.example.crm_project.modules.contacts.domain.entity.Contact;
import org.example.crm_project.modules.contacts.infrastructure.persistence.entity.ContactEntity;
import org.springframework.stereotype.Component;

@Component
public class ContactJpaMapper {
    public Contact toDomain(ContactEntity e) {
        Contact contact = new Contact();
        contact.setId(e.getId());
        contact.setCustomerId(e.getCustomer().getId());
        contact.setFullName(e.getFullName());
        contact.setPosition(e.getPosition());
        contact.setEmail(e.getEmail());
        contact.setPhone(e.getPhone());
        contact.setDob(e.getDob());
        contact.setNote(e.getNotes());
        contact.setPrimary(e.isPrimary());
        contact.setCreatedBy(e.getCreatedBy());
        contact.setUpdatedBy(e.getUpdatedBy());
        contact.setCreatedAt(e.getCreatedAt());
        contact.setUpdatedAt(e.getUpdatedAt());
        contact.setActive(e.isActive());
        contact.setDeletedAt(e.getDeletedAt());
        return contact;
    }
}
