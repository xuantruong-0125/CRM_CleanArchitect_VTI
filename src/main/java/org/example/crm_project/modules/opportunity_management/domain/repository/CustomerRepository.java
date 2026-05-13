package org.example.crm_project.modules.opportunity_management.domain.repository;

import org.example.crm_project.modules.opportunity_management.domain.entity.Customer;

import java.util.List;
import java.util.Optional;

/**
 * Domain Repository Interface (Port) – Customer.
 */
public interface CustomerRepository {
    List<Customer> findAll();
    Optional<Customer> findById(Integer id);
}
