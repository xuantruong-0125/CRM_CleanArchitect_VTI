package org.example.crm_project.modules.contacts_managerment.application.interfaces;

import org.example.crm_project.modules.contacts_managerment.application.dto.request.CreateContactRequest;
import org.example.crm_project.modules.contacts_managerment.application.dto.request.UpdateContactRequest;
import org.example.crm_project.modules.contacts_managerment.application.dto.response.ContactResponse;
import org.example.crm_project.modules.contacts_managerment.application.dto.response.PageResponse;

public interface ContactService extends BaseService<CreateContactRequest, UpdateContactRequest, ContactResponse, Long> {
    PageResponse<ContactResponse> search(String keyword, int page, int size);
}
