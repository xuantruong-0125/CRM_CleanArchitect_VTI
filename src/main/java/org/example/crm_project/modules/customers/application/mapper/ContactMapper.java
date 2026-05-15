package org.example.crm_project.modules.customers.application.mapper;

import org.example.crm_project.modules.customers.application.dto.response.ContactResponseDTO;
import org.example.crm_project.modules.customers.application.dto.request.CreateContactDTO;
import org.example.crm_project.modules.customers.domain.entity.Contact;
import org.springframework.stereotype.Component;

/**
 * Mapper: ContactMapper
 */
@Component
public class ContactMapper {

    /**
     * Chuyển CreateContactDTO thành Contact entity
     */
    public Contact toEntity(CreateContactDTO createDTO, Long customerId) {
        if (createDTO == null) {
            return null;
        }

        Contact contact = new Contact();
        contact.setCustomerId(customerId);
        contact.setFullName(createDTO.getFullName());
        contact.setPhone(createDTO.getPhone());
        contact.setEmail(createDTO.getEmail());
        contact.setPosition(createDTO.getPosition());
        contact.setAddress(createDTO.getAddress());
        contact.setDateOfBirth(createDTO.getDateOfBirth());
        contact.setNotes(createDTO.getNotes());
        contact.setIsPrimary(createDTO.getIsPrimary() != null ? createDTO.getIsPrimary() : false);

        return contact;
    }

    /**
     * Chuyển Contact entity thành ContactResponseDTO
     */
    public ContactResponseDTO toResponseDTO(Contact contact) {
        if (contact == null) {
            return null;
        }

        ContactResponseDTO dto = new ContactResponseDTO();
        dto.setId(contact.getId());
        dto.setCustomerId(contact.getCustomerId());
        dto.setFullName(contact.getFullName());
        dto.setPosition(contact.getPosition());
        dto.setPhone(contact.getPhone());
        dto.setEmail(contact.getEmail());
        dto.setAddress(contact.getAddress());
        dto.setDateOfBirth(contact.getDateOfBirth());
        dto.setIsPrimary(contact.getIsPrimary());
        dto.setCreatedAt(contact.getCreatedAt());

        return dto;
    }

    /**
     * Cập nhật Contact entity từ DTO
     */
    public Contact updateEntityFromDTO(CreateContactDTO updateDTO, Contact contact) {
        if (updateDTO == null || contact == null) {
            return contact;
        }

        if (updateDTO.getFullName() != null) {
            contact.setFullName(updateDTO.getFullName());
        }
        if (updateDTO.getPhone() != null) {
            contact.setPhone(updateDTO.getPhone());
        }
        if (updateDTO.getEmail() != null) {
            contact.setEmail(updateDTO.getEmail());
        }
        if (updateDTO.getPosition() != null) {
            contact.setPosition(updateDTO.getPosition());
        }
        if (updateDTO.getAddress() != null) {
            contact.setAddress(updateDTO.getAddress());
        }
        if (updateDTO.getDateOfBirth() != null) {
            contact.setDateOfBirth(updateDTO.getDateOfBirth());
        }
        if (updateDTO.getNotes() != null) {
            contact.setNotes(updateDTO.getNotes());
        }
        if (updateDTO.getIsPrimary() != null) {
            contact.setIsPrimary(updateDTO.getIsPrimary());
        }

        return contact;
    }
}
