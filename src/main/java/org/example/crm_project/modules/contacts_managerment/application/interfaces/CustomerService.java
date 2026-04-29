package org.example.crm_project.modules.contacts_managerment.application.interfaces;

import org.example.crm_project.modules.contacts_managerment.domain.entity.Customer;
import java.util.List;

public interface CustomerService {
    Customer create(Customer customer);
    List<Customer> getAll();
    Customer getById(Long id);
}
