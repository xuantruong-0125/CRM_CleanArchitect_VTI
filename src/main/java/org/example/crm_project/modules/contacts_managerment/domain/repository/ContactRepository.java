package org.example.crm_project.modules.contacts_managerment.domain.repository;

import org.example.crm_project.modules.contacts_managerment.domain.entity.Contact;
import java.util.List;

public interface ContactRepository extends BaseRepository<Contact, Long> {
    List<Contact> search(String keyword, int page, int size);
    long countSearch(String keyword);
}
