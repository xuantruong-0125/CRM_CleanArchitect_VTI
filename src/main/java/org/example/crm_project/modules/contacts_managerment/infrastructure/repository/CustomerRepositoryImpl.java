package org.example.crm_project.modules.contacts_managerment.infrastructure.repository;

import org.example.crm_project.modules.contacts_managerment.domain.entity.Customer;
import org.example.crm_project.modules.contacts_managerment.domain.repository.CustomerRepository;
import org.example.crm_project.modules.contacts_managerment.infrastructure.persistence.mapper.CustomerPersistenceMapper;
import org.example.crm_project.modules.contacts_managerment.infrastructure.persistence.repository.JpaCustomerRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class CustomerRepositoryImpl implements CustomerRepository {

    private final JpaCustomerRepository jpaCustomerRepository;
    private final CustomerPersistenceMapper customerPersistenceMapper;

    public CustomerRepositoryImpl(JpaCustomerRepository jpaCustomerRepository, CustomerPersistenceMapper customerPersistenceMapper) {
        this.jpaCustomerRepository = jpaCustomerRepository;
        this.customerPersistenceMapper = customerPersistenceMapper;
    }

    @Override
    public Optional<Customer> findById(Long id) {
        return jpaCustomerRepository.findById(id)
                .map(customerPersistenceMapper::toDomain);
    }
}
