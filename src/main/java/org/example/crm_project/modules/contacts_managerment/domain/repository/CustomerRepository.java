package org.example.crm_project.modules.contacts_managerment.domain.repository;

import org.example.crm_project.modules.contacts_managerment.domain.entity.Customer;
import java.util.Optional;

public interface CustomerRepository {
    Optional<Customer> findById(Long id);
}
