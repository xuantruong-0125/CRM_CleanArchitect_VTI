package org.example.crm_project.modules.contacts_managerment.application.service;

import org.example.crm_project.modules.contacts_managerment.application.interfaces.CustomerService;
import org.example.crm_project.modules.contacts_managerment.domain.entity.Customer;
import org.example.crm_project.modules.contacts_managerment.infrastructure.persistence.mapper.CustomerPersistenceMapper;
import org.example.crm_project.modules.contacts_managerment.infrastructure.persistence.repository.JpaCustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final JpaCustomerRepository jpaCustomerRepository;
    private final CustomerPersistenceMapper customerPersistenceMapper;

    public CustomerServiceImpl(JpaCustomerRepository jpaCustomerRepository, CustomerPersistenceMapper customerPersistenceMapper) {
        this.jpaCustomerRepository = jpaCustomerRepository;
        this.customerPersistenceMapper = customerPersistenceMapper;
    }

    @Override
    public Customer create(Customer customer) {
        var entity = customerPersistenceMapper.toEntity(customer);
        var saved = jpaCustomerRepository.save(entity);
        return customerPersistenceMapper.toDomain(saved);
    }

    @Override
    public List<Customer> getAll() {
        return jpaCustomerRepository.findAll().stream()
                .map(customerPersistenceMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Customer getById(Long id) {
        return jpaCustomerRepository.findById(id)
                .map(customerPersistenceMapper::toDomain)
                .orElse(null);
    }
}
