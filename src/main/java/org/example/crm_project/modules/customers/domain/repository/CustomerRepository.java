package org.example.crm_project.modules.customers.domain.repository;

import org.example.crm_project.modules.customers.domain.entity.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository {
    Optional<Customer> findById(Long id);
    List<Customer> findByIds(java.util.Collection<Long> ids);
    List<Customer> findAll();
    List<Customer> findAllActive();
    List<Customer> searchCustomers(String keyword, int limit);
    Customer save(Customer customer);
    void deleteById(Long id);
}
