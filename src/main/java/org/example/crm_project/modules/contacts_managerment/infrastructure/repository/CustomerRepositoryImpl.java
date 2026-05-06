package org.example.crm_project.modules.contacts_managerment.infrastructure.repository;

import org.example.crm_project.modules.contacts_managerment.domain.entity.Customer;
import org.example.crm_project.modules.contacts_managerment.domain.entity.CustomerType;
import org.example.crm_project.modules.contacts_managerment.domain.repository.CustomerRepository;
import org.example.crm_project.modules.contacts_managerment.infrastructure.persistence.entity.CustomerEntity;
import org.example.crm_project.modules.contacts_managerment.infrastructure.persistence.mapper.CustomerPersistenceMapper;
import org.example.crm_project.modules.contacts_managerment.infrastructure.persistence.repository.JpaCustomerRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class CustomerRepositoryImpl implements CustomerRepository {

    private final JpaCustomerRepository jpaCustomerRepository;
    private final CustomerPersistenceMapper customerPersistenceMapper;

    public CustomerRepositoryImpl(JpaCustomerRepository jpaCustomerRepository, CustomerPersistenceMapper customerPersistenceMapper) {
        this.jpaCustomerRepository = jpaCustomerRepository;
        this.customerPersistenceMapper = customerPersistenceMapper;
    }

    @Override
    public Customer save(Customer entity) {
        CustomerEntity customerEntity = customerPersistenceMapper.toEntity(entity);
        CustomerEntity savedEntity = jpaCustomerRepository.save(customerEntity);
        return customerPersistenceMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Customer> findById(Long id) {
        return jpaCustomerRepository.findById(id)
                .map(customerPersistenceMapper::toDomain);
    }

    @Override
    public List<Customer> findAll() {
        return jpaCustomerRepository.findAllActive().stream()
                .map(customerPersistenceMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Customer> findAll(int page, int size) {
        return jpaCustomerRepository.findAllActive(PageRequest.of(page - 1, size)).getContent().stream()
                .map(customerPersistenceMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public long count() {
        return jpaCustomerRepository.count();
    }

    @Override
    public void deleteById(Long id) {
        jpaCustomerRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return jpaCustomerRepository.existsById(id);
    }

    @Override
    public List<Customer> search(String keyword, CustomerType type, int page, int size) {
        return jpaCustomerRepository.searchCustomers(keyword, type, PageRequest.of(page - 1, size))
                .getContent()
                .stream()
                .map(customerPersistenceMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public long countSearch(String keyword, CustomerType type) {
        return jpaCustomerRepository.countSearchCustomers(keyword, type);
    }
}
