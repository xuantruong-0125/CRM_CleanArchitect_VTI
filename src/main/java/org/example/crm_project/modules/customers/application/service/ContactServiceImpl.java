package org.example.crm_project.modules.customers.application.service;

import org.example.crm_project.modules.customers.application.dto.response.ContactResponseDTO;
import org.example.crm_project.modules.customers.application.dto.request.CreateContactDTO;
import org.example.crm_project.modules.customers.application.mapper.ContactMapper;
import org.example.crm_project.modules.customers.domain.entity.Contact;
import org.example.crm_project.modules.customers.domain.exception.ContactNotFoundException;
import org.example.crm_project.modules.customers.domain.exception.InvalidCustomerException;
import org.example.crm_project.modules.customers.domain.repository.ContactRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation: ContactServiceImpl
 */
@Service
@Transactional
public class ContactServiceImpl implements ContactService {

    private final ContactRepository contactRepository;
    private final ContactMapper contactMapper;

    public ContactServiceImpl(ContactRepository contactRepository, ContactMapper contactMapper) {
        this.contactRepository = contactRepository;
        this.contactMapper = contactMapper;
    }

    @Override
    public ContactResponseDTO createContact(Long customerId, CreateContactDTO createDTO) {
        if (createDTO == null || createDTO.getFullName() == null) {
            throw new InvalidCustomerException("Tên người liên hệ là bắt buộc");
        }

        Contact contact = contactMapper.toEntity(createDTO, customerId);
        contact.setCreatedAt(LocalDateTime.now());

        Contact savedContact = contactRepository.save(contact);
        return contactMapper.toResponseDTO(savedContact);
    }

    @Override
    public ContactResponseDTO updateContact(Long contactId, CreateContactDTO updateDTO) {
        Contact contact = contactRepository.findById(contactId)
                .orElseThrow(() -> new ContactNotFoundException(contactId));

        contact = contactMapper.updateEntityFromDTO(updateDTO, contact);
        contact.setUpdatedAt(LocalDateTime.now());

        Contact updatedContact = contactRepository.save(contact);
        return contactMapper.toResponseDTO(updatedContact);
    }

    @Override
    @Transactional(readOnly = true)
    public ContactResponseDTO getContactById(Long contactId) {
        Contact contact = contactRepository.findById(contactId)
                .orElseThrow(() -> new ContactNotFoundException(contactId));
        return contactMapper.toResponseDTO(contact);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ContactResponseDTO> getContactsByCustomer(Long customerId) {
        List<Contact> contacts = contactRepository.findByCustomerId(customerId);
        return contacts.stream()
                .map(contactMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ContactResponseDTO> getContactsByCustomerPaginated(Long customerId, Pageable pageable) {
        Page<Contact> contacts = contactRepository.findByCustomerId(customerId, pageable);
        return contacts.map(contactMapper::toResponseDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public ContactResponseDTO getPrimaryContact(Long customerId) {
        Contact contact = contactRepository.findPrimaryContact(customerId)
                .orElseThrow(() -> new ContactNotFoundException(customerId));
        return contactMapper.toResponseDTO(contact);
    }

    @Override
    public void deleteContact(Long contactId) {
        if (!contactRepository.existsById(contactId)) {
            throw new ContactNotFoundException(contactId);
        }
        contactRepository.delete(contactId);
    }

    @Override
    public void deleteAllContactsByCustomer(Long customerId) {
        contactRepository.deleteByCustomerId(customerId);
    }

    @Override
    public void setPrimaryContact(Long contactId) {
        Contact contact = contactRepository.findById(contactId)
                .orElseThrow(() -> new ContactNotFoundException(contactId));

        Long customerId = contact.getCustomerId();
        
        // Bỏ đánh dấu chính cho tất cả người liên hệ khác
        List<Contact> allContacts = contactRepository.findByCustomerId(customerId);
        allContacts.forEach(c -> c.setIsPrimary(false));

        // Đánh dấu người liên hệ hiện tại là chính
        contact.setIsPrimary(true);
        contact.setUpdatedAt(LocalDateTime.now());
        contactRepository.save(contact);
    }

    @Override
    @Transactional(readOnly = true)
    public long getTotalContactsCount(Long customerId) {
        return contactRepository.countByCustomerId(customerId);
    }
}
