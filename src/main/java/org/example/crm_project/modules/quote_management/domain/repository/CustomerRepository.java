package org.example.crm_project.modules.quote_management.domain.repository;

import org.example.crm_project.modules.quote_management.domain.entity.Customer;

import java.util.List;
import java.util.Optional;

/**
 * Domain repository interface cho Customer (read-only trong module báo giá).
 */
public interface CustomerRepository {

    List<Customer> findAllActive();

    Optional<Customer> findById(Integer id);
}
