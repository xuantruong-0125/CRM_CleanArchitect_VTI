package org.example.crm_project.modules.opportunity_management.infrastructure.repository;

import org.example.crm_project.modules.opportunity_management.domain.entity.Customer;
import org.example.crm_project.modules.opportunity_management.domain.repository.CustomerRepository;
import org.example.crm_project.modules.opportunity_management.infrastructure.persistence.repository.JpaOpportunityCustomerRepository;
import org.example.crm_project.modules.opportunity_management.infrastructure.persistence.mapper.CustomerEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Repository Adapter – Customer.
 */
@Component
@RequiredArgsConstructor
public class CustomerRepositoryAdapter implements CustomerRepository {

    private final JpaOpportunityCustomerRepository jpaRepository;
    private final CustomerEntityMapper mapper;

    @Override
    public List<Customer> findAll() {
        return jpaRepository.findAll().stream()
                .map(mapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public Optional<Customer> findById(Integer id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }
}
