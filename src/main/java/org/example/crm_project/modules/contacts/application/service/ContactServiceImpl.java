package org.example.crm_project.modules.contacts.application.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.crm_project.modules.contacts.application.dto.response.ContactResponse;
import org.example.crm_project.modules.contacts.application.interfaces.ContactService;
import org.example.crm_project.modules.contacts.application.mapper.ContactMapper;
import org.example.crm_project.modules.contacts.domain.repository.ContactRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ContactServiceImpl implements ContactService {
    ContactRepository contactRepository;
    ContactMapper contactMapper;

    @Override
    public List<ContactResponse> findAll() {
        return contactRepository.findAll().stream()
                .map(contactMapper::toResponse)
                .toList();
    }
}
