package org.example.crm_project.modules.contacts_managerment.application.mapper;

import org.example.crm_project.modules.contacts_managerment.application.dto.request.CreateContactRequest;
import org.example.crm_project.modules.contacts_managerment.application.dto.request.UpdateContactRequest;
import org.example.crm_project.modules.contacts_managerment.application.dto.response.ContactResponse;
import org.example.crm_project.modules.contacts_managerment.domain.entity.Contact;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ContactMapper {

    public Contact toEntity(CreateContactRequest request) {
        if (request == null)
            return null;
        Contact contact = new Contact();
        contact.setFullName(request.getFullName());
        contact.setPosition(request.getPosition());
        contact.setPhone(request.getPhone());
        contact.setEmail(request.getEmail());
        contact.setAddress(request.getAddress());
        contact.setDob(request.getDob());
        contact.setNotes(request.getNotes());
        contact.setPrimary(request.isPrimary());
        contact.setActive(request.isActive());
        contact.setCreatedAt(LocalDateTime.now());
        return contact;
    }

    public void updateEntity(Contact contact, UpdateContactRequest request) {
        if (request == null)
            return;
        if (request.getFullName() != null)
            contact.setFullName(request.getFullName());
        if (request.getPosition() != null)
            contact.setPosition(request.getPosition());
        if (request.getPhone() != null)
            contact.setPhone(request.getPhone());
        if (request.getEmail() != null)
            contact.setEmail(request.getEmail());
        if (request.getAddress() != null)
            contact.setAddress(request.getAddress());
        if (request.getDob() != null)
            contact.setDob(request.getDob());
        if (request.getNotes() != null)
            contact.setNotes(request.getNotes());
        contact.setPrimary(request.isPrimary());
        contact.setActive(request.isActive());
        contact.setUpdatedAt(LocalDateTime.now());
    }

    public ContactResponse toResponse(Contact contact) {
        if (contact == null)
            return null;
        ContactResponse.ContactResponseBuilder builder = ContactResponse.builder()
                .id(contact.getId())
                .fullName(contact.getFullName())
                .position(contact.getPosition())
                .phone(contact.getPhone())
                .email(contact.getEmail())
                .address(contact.getAddress())
                .dob(contact.getDob())
                .notes(contact.getNotes())
                .isPrimary(contact.isPrimary())
                .isActive(contact.isActive());

        if (contact.getCustomer() != null) {
            builder.customerId(contact.getCustomer().getId());
            builder.customerName(contact.getCustomer().getName());
        }

        return builder.build();
    }
}
