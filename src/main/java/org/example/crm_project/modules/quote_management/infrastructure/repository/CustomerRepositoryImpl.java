package org.example.crm_project.modules.quote_management.infrastructure.repository;

import lombok.RequiredArgsConstructor;
import org.example.crm_project.modules.quote_management.domain.entity.Customer;
import org.example.crm_project.modules.quote_management.domain.repository.CustomerRepository;
import org.example.crm_project.modules.quote_management.infrastructure.persistence.mapper.CustomerJpaMapper;
import org.example.crm_project.modules.quote_management.infrastructure.persistence.repository.JpaCustomerRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Implementation của CustomerRepository (domain interface).
 */
@Repository
@RequiredArgsConstructor
public class CustomerRepositoryImpl implements CustomerRepository {

    private final JpaCustomerRepository jpaRepository;

    @Override
    public List<Customer> findAllActive() {
        return jpaRepository.findAllActive()
                .stream()
                .map(CustomerJpaMapper::toDomain)
                .toList();
    }

    @Override
    public Optional<Customer> findById(Integer id) {
        return jpaRepository.findById(id)
                .map(CustomerJpaMapper::toDomain);
    }
}
