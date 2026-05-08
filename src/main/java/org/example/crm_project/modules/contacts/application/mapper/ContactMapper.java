package org.example.crm_project.modules.contacts.application.mapper;

import org.example.crm_project.modules.contacts.application.dto.response.ContactResponse;
import org.example.crm_project.modules.contacts.domain.entity.Contact;
import org.springframework.stereotype.Component;

@Component
public class ContactMapper {
    public ContactResponse toResponse(Contact contact) {
        return  ContactResponse.builder()
                .id(contact.getId())
                .customerId(contact.getCustomerId())
                .fullName(contact.getFullName())
                .position(contact.getPosition())
                .email(contact.getEmail())
                .phone(contact.getPhone())
                .dob(contact.getDob())
                .note(contact.getNote())
                .primary(contact.isPrimary())
                .createdBy(contact.getCreatedBy())
                .updatedBy(contact.getUpdatedBy())
                .active(contact.isActive())
                .createdAt(contact.getCreatedAt())
                .updatedAt(contact.getUpdatedAt())
                .deletedAt(contact.getDeletedAt())
                .build();
    }
}
