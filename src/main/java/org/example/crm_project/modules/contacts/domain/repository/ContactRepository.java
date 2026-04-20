package org.example.crm_project.modules.contacts.domain.repository;


import org.example.crm_project.modules.contacts.domain.entity.Contact;

import java.util.List;

public interface ContactRepository {
    List<Contact> findAll();
}
