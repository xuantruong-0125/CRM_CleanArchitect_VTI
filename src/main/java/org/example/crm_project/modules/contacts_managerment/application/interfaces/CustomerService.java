package org.example.crm_project.modules.contacts_managerment.application.interfaces;

import org.example.crm_project.modules.contacts_managerment.application.dto.request.CreateCustomerRequest;
import org.example.crm_project.modules.contacts_managerment.application.dto.request.UpdateCustomerRequest;
import org.example.crm_project.modules.contacts_managerment.application.dto.response.CustomerResponse;
import org.example.crm_project.modules.contacts_managerment.application.dto.response.PageResponse;
import org.example.crm_project.modules.contacts_managerment.domain.entity.CustomerType;

public interface CustomerService extends BaseService<CreateCustomerRequest, UpdateCustomerRequest, CustomerResponse, Long> {
    PageResponse<CustomerResponse> search(String keyword, CustomerType type, int page, int size);
}
