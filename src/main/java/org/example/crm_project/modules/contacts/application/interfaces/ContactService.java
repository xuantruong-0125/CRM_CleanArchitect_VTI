package org.example.crm_project.modules.contacts.application.interfaces;

import org.example.crm_project.modules.contacts.application.dto.response.ContactResponse;

import java.util.List;

public interface ContactService {
    List<ContactResponse> findAll();
}
