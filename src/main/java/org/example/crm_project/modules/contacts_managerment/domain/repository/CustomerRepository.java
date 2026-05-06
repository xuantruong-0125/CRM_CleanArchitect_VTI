package org.example.crm_project.modules.contacts_managerment.domain.repository;

import org.example.crm_project.modules.contacts_managerment.domain.entity.Customer;
import org.example.crm_project.modules.contacts_managerment.domain.entity.CustomerType;
import java.util.List;

public interface CustomerRepository extends BaseRepository<Customer, Long> {
    List<Customer> search(String keyword, CustomerType type, int page, int size);
    long countSearch(String keyword, CustomerType type);
}
