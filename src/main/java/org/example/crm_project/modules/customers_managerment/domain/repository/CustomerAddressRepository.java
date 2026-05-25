package org.example.crm_project.modules.customers_managerment.domain.repository;

import org.example.crm_project.modules.customers_managerment.domain.entity.CustomerAddress;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Repository Interface: CustomerAddressRepository
 */
public interface CustomerAddressRepository {
    CustomerAddress save(CustomerAddress address);
    Optional<CustomerAddress> findById(Long id);
    List<CustomerAddress> findByCustomerId(Long customerId);
    Page<CustomerAddress> findByCustomerId(Long customerId, Pageable pageable);
    Optional<CustomerAddress> findPrimaryAddress(Long customerId);
    void delete(Long id);
    void deleteByCustomerId(Long customerId);
    boolean existsById(Long id);
    long count();
}
