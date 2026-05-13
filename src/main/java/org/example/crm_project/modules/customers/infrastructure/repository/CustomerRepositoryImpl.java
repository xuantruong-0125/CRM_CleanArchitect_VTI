package org.example.crm_project.modules.customers.infrastructure.repository;

import lombok.RequiredArgsConstructor;
import org.example.crm_project.modules.customers.domain.entity.Customer;
import org.example.crm_project.modules.customers.domain.repository.CustomerRepository;
import org.example.crm_project.modules.customers.infrastructure.persistence.entity.CustomerEntity;
import org.example.crm_project.modules.customers.infrastructure.persistence.mapper.CustomerJpaMapper;
import org.example.crm_project.modules.customers.infrastructure.persistence.repository.JpaCustomerRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class CustomerRepositoryImpl implements CustomerRepository {

    private final JpaCustomerRepository jpaCustomerRepository;
    private final CustomerJpaMapper customerJpaMapper;

    @Override
    public Optional<Customer> findById(Long id) {
        return jpaCustomerRepository.findById(id)
                .map(customerJpaMapper::toDomain);
    }

    @Override
    public List<Customer> findByIds(java.util.Collection<Long> ids) {
        return jpaCustomerRepository.findAllById(ids).stream()
                .map(customerJpaMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Customer> findAll() {
        return jpaCustomerRepository.findAll().stream()
                .map(customerJpaMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Customer> findAllActive() {
        return jpaCustomerRepository.findByDeletedAtIsNull().stream()
                .map(customerJpaMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Customer> searchCustomers(String keyword, int limit) {
        return jpaCustomerRepository.searchTop10(keyword, org.springframework.data.domain.PageRequest.of(0, limit)).stream()
                .map(customerJpaMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Customer save(Customer customer) {
        CustomerEntity entity = customerJpaMapper.toEntity(customer);
        CustomerEntity savedEntity = jpaCustomerRepository.save(entity);
        return customerJpaMapper.toDomain(savedEntity);
    }

    @Override
    public void deleteById(Long id) {
        jpaCustomerRepository.deleteById(id);
    }
}
