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
import org.example.crm_project.modules.customers_managerment.domain.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContactServiceImpl implements ContactService {

    private final ContactRepository contactRepository;
    private final CustomerRepository customerRepository;
    private final ContactMapper contactMapper;

    public ContactServiceImpl(ContactRepository contactRepository, CustomerRepository customerRepository,
            ContactMapper contactMapper) {
        this.contactRepository = contactRepository;
        this.customerRepository = customerRepository;
        this.contactMapper = contactMapper;
    }

    @Override
    public ContactResponse create(CreateContactRequest request) {
        Contact contact = contactMapper.toEntity(request);
        if (request.getCustomerId() != null) {
            contact.setCustomer(customerRepository.findById(request.getCustomerId())
                    .orElseThrow(() -> new RuntimeException("Customer not found with id: " + request.getCustomerId())));
        }
        Contact savedContact = contactRepository.save(contact);
        return contactMapper.toResponse(savedContact);
    }

    @Override
    public ContactResponse update(Long id, UpdateContactRequest request) {
        Contact contact = contactRepository.findById(id)
                .orElseThrow(() -> new ContactNotFoundException("Contact not found with id: " + id));
        contactMapper.updateEntity(contact, request);
        if (request.getCustomerId() != null) {
            contact.setCustomer(customerRepository.findById(request.getCustomerId())
                    .orElseThrow(() -> new RuntimeException("Customer not found with id: " + request.getCustomerId())));
        }
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
    public PageResponse<ContactResponse> search(String keyword, Boolean isPrimary, int page, int size) {
        List<Contact> contacts = contactRepository.search(keyword, isPrimary, page, size);
        long totalItems = contactRepository.countSearch(keyword, isPrimary);
        List<ContactResponse> contactResponses = contacts.stream()
                .map(contactMapper::toResponse)
                .collect(Collectors.toList());
        return PageResponse.of(contactResponses, page, size, totalItems);
    }
}
