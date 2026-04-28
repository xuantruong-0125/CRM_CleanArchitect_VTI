package org.example.crm_project.modules.contacts_managerment.application.service;

import org.example.crm_project.modules.contacts_managerment.application.dto.request.CreateContactRequest;
import org.example.crm_project.modules.contacts_managerment.application.dto.request.UpdateContactRequest;
import org.example.crm_project.modules.contacts_managerment.application.dto.response.ContactResponse;
import org.example.crm_project.modules.contacts_managerment.application.dto.response.PageResponse;
import org.example.crm_project.modules.contacts_managerment.application.interfaces.ContactService;
import org.example.crm_project.modules.contacts_managerment.application.mapper.ContactMapper;
import org.example.crm_project.modules.contacts_managerment.domain.entity.Contact;
import org.example.crm_project.modules.contacts_managerment.domain.exception.ContactNotFoundException;
import org.example.crm_project.modules.contacts_managerment.domain.repository.ContactRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContactServiceImpl implements ContactService {

    private final ContactRepository contactRepository;
    private final ContactMapper contactMapper;

    public ContactServiceImpl(ContactRepository contactRepository, ContactMapper contactMapper) {
        this.contactRepository = contactRepository;
        this.contactMapper = contactMapper;
    }

    @Override
    public ContactResponse create(CreateContactRequest request) {
        Contact contact = contactMapper.toEntity(request);
        Contact savedContact = contactRepository.save(contact);
        return contactMapper.toResponse(savedContact);
    }

    @Override
    public ContactResponse update(Long id, UpdateContactRequest request) {
        Contact contact = contactRepository.findById(id)
                .orElseThrow(() -> new ContactNotFoundException("Contact not found with id: " + id));
        contactMapper.updateEntity(contact, request);
        Contact updatedContact = contactRepository.save(contact);
        return contactMapper.toResponse(updatedContact);
    }

    @Override
    public void delete(Long id) {
        Contact contact = contactRepository.findById(id)
                .orElseThrow(() -> new ContactNotFoundException("Contact not found with id: " + id));
        contact.setActive(false);
        contact.setDeletedAt(LocalDateTime.now());
        contactRepository.save(contact);
    }

    @Override
    public void deleteByIds(List<Long> ids) {
        for (Long id : ids) {
            delete(id);
        }
    }

    @Override
    public ContactResponse getById(Long id) {
        Contact contact = contactRepository.findById(id)
                .orElseThrow(() -> new ContactNotFoundException("Contact not found with id: " + id));
        return contactMapper.toResponse(contact);
    }

    @Override
    public List<ContactResponse> getAll() {
        List<Contact> contacts = contactRepository.findAll();
        return contacts.stream()
                .map(contactMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public PageResponse<ContactResponse> search(String keyword, int page, int size) {
        List<Contact> contacts = contactRepository.search(keyword, page, size);
        long totalItems = contactRepository.countSearch(keyword);
        List<ContactResponse> contactResponses = contacts.stream()
                .map(contactMapper::toResponse)
                .collect(Collectors.toList());
        return PageResponse.of(contactResponses, page, size, totalItems);
    }
}
