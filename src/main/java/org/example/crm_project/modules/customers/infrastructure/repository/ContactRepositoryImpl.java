package org.example.crm_project.modules.customers.infrastructure.repository;

import org.example.crm_project.modules.customers.domain.entity.Contact;
import org.example.crm_project.modules.customers.domain.repository.ContactRepository;
import org.example.crm_project.modules.customers.infrastructure.persistence.entity.ContactEntity;
import org.example.crm_project.modules.customers.infrastructure.persistence.jpa.ContactJpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Repository Implementation: ContactRepositoryImpl
 */
@Repository
public class ContactRepositoryImpl implements ContactRepository {

    private final ContactJpaRepository jpaRepository;

    public ContactRepositoryImpl(ContactJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Contact save(Contact contact) {
        ContactEntity entity = domainToEntity(contact);
        ContactEntity saved = jpaRepository.save(entity);
        return entityToDomain(saved);
    }

    @Override
    public Optional<Contact> findById(Long id) {
        return jpaRepository.findById(id).map(this::entityToDomain);
    }

    @Override
    public List<Contact> findByCustomerId(Long customerId) {
        return jpaRepository.findByCustomerId(customerId).stream()
                .map(this::entityToDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Page<Contact> findByCustomerId(Long customerId, Pageable pageable) {
        Page<ContactEntity> page = jpaRepository.findByCustomerId(customerId, pageable);
        return page.map(this::entityToDomain);
    }

    @Override
    public Optional<Contact> findPrimaryContact(Long customerId) {
        return jpaRepository.findPrimaryContact(customerId).map(this::entityToDomain);
    }

    @Override
    public Optional<Contact> findByEmail(String email) {
        return jpaRepository.findByEmail(email).map(this::entityToDomain);
    }

    @Override
    public List<Contact> findByPhone(String phone) {
        return jpaRepository.findByPhone(phone).stream()
                .map(this::entityToDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        Optional<ContactEntity> entity = jpaRepository.findById(id);
        entity.ifPresent(e -> {
            e.setDeletedAt(LocalDateTime.now());
            jpaRepository.save(e);
        });
    }

    @Override
    public void deleteByCustomerId(Long customerId) {
        List<ContactEntity> contacts = jpaRepository.findByCustomerId(customerId);
        contacts.forEach(c -> {
            c.setDeletedAt(LocalDateTime.now());
            jpaRepository.save(c);
        });
    }

    @Override
    public boolean existsById(Long id) {
        return jpaRepository.existsById(id);
    }

    @Override
    public long countByCustomerId(Long customerId) {
        return jpaRepository.countByCustomerId(customerId);
    }

    // Helper methods
    private Contact entityToDomain(ContactEntity entity) {
        if (entity == null) return null;

        Contact contact = new Contact();
        contact.setId(entity.getId());
        contact.setCustomerId(entity.getCustomerId());
        contact.setFullName(entity.getFullName());
        contact.setPosition(entity.getPosition());
        contact.setPhone(entity.getPhone());
        contact.setEmail(entity.getEmail());
        contact.setAddress(entity.getAddress());
        contact.setDateOfBirth(entity.getDateOfBirth());
        contact.setNotes(entity.getNotes());
        contact.setIsPrimary(entity.getIsPrimary());
        contact.setCreatedBy(entity.getCreatedBy());
        contact.setUpdatedBy(entity.getUpdatedBy());
        contact.setCreatedAt(entity.getCreatedAt());
        contact.setUpdatedAt(entity.getUpdatedAt());
        contact.setDeletedAt(entity.getDeletedAt());

        return contact;
    }

    private ContactEntity domainToEntity(Contact contact) {
        if (contact == null) return null;

        ContactEntity entity = new ContactEntity();
        entity.setId(contact.getId());
        entity.setCustomerId(contact.getCustomerId());
        entity.setFullName(contact.getFullName());
        entity.setPosition(contact.getPosition());
        entity.setPhone(contact.getPhone());
        entity.setEmail(contact.getEmail());
        entity.setAddress(contact.getAddress());
        entity.setDateOfBirth(contact.getDateOfBirth());
        entity.setNotes(contact.getNotes());
        entity.setIsPrimary(contact.getIsPrimary());
        entity.setCreatedBy(contact.getCreatedBy());
        entity.setUpdatedBy(contact.getUpdatedBy());
        entity.setCreatedAt(contact.getCreatedAt());
        entity.setUpdatedAt(contact.getUpdatedAt());
        entity.setDeletedAt(contact.getDeletedAt());

        return entity;
    }
}
