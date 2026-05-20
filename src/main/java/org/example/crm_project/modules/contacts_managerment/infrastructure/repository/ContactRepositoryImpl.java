package org.example.crm_project.modules.contacts_managerment.infrastructure.repository;

import org.example.crm_project.modules.contacts_managerment.domain.entity.Contact;
import org.example.crm_project.modules.contacts_managerment.domain.repository.ContactRepository;
import org.example.crm_project.modules.contacts_managerment.infrastructure.persistence.entity.ContactEntity;
import org.example.crm_project.modules.contacts_managerment.infrastructure.persistence.mapper.ContactPersistenceMapper;
import org.example.crm_project.modules.contacts_managerment.infrastructure.persistence.repository.ContactJpaRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class ContactRepositoryImpl implements ContactRepository {

    private final ContactJpaRepository contactJpaRepository;
    private final ContactPersistenceMapper mapper;

    public ContactRepositoryImpl(ContactJpaRepository contactJpaRepository, ContactPersistenceMapper mapper) {
        this.contactJpaRepository = contactJpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Contact save(Contact entity) {
        ContactEntity contactEntity = mapper.toEntity(entity);
        ContactEntity savedEntity = contactJpaRepository.save(contactEntity);
        return mapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Contact> findById(Long id) {
        return contactJpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<Contact> findAll() {
        return contactJpaRepository.findAllActive().stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Contact> findAll(int page, int size) {
        return contactJpaRepository.findAllActive(PageRequest.of(page - 1, size)).getContent().stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public long count() {
        return contactJpaRepository.count();
    }

    @Override
    public void deleteById(Long id) {
        contactJpaRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return contactJpaRepository.existsById(id);
    }

    @Override
    public List<Contact> search(String keyword, Boolean isPrimary, int page, int size) {
        return contactJpaRepository.searchContacts(keyword, isPrimary, PageRequest.of(page - 1, size))
                .getContent()
                .stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public long countSearch(String keyword, Boolean isPrimary) {
        return contactJpaRepository.countSearchContacts(keyword, isPrimary);
    }
}
