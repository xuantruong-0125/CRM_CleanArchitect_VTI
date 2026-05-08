package org.example.crm_project.modules.contacts.infrastructure.repository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.crm_project.modules.contacts.domain.entity.Contact;
import org.example.crm_project.modules.contacts.domain.repository.ContactRepository;
import org.example.crm_project.modules.contacts.infrastructure.persistence.mapper.ContactJpaMapper;
import org.example.crm_project.modules.contacts.infrastructure.persistence.repository.ContactJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
@RequiredArgsConstructor
@FieldDefaults(level =  AccessLevel.PRIVATE, makeFinal = true)
public class ContactRepositoryImpl implements ContactRepository {
    ContactJpaRepository contactJpaRepository;

    ContactJpaMapper contactJpaMapper;
    @Override
    public List<Contact> findAll() {
        return contactJpaRepository.findAll().stream()
                .map(contactJpaMapper::toDomain)
                .toList();
    }
}
