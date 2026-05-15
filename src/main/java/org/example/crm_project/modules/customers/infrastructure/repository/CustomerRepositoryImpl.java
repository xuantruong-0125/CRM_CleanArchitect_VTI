package org.example.crm_project.modules.customers.infrastructure.repository;

import org.example.crm_project.modules.customers.domain.entity.Customer;
import org.example.crm_project.modules.customers.domain.repository.CustomerRepository;
import org.example.crm_project.modules.customers.infrastructure.persistence.entity.CustomerEntity;
import org.example.crm_project.modules.customers.infrastructure.persistence.jpa.CustomerJpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Repository Implementation: CustomerRepositoryImpl
 * Implements domain repository interface using JPA
 */
@Repository
public class CustomerRepositoryImpl implements CustomerRepository {

    private final CustomerJpaRepository jpaRepository;

    public CustomerRepositoryImpl(CustomerJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Customer save(Customer customer) {
        CustomerEntity entity = domainToEntity(customer);
        CustomerEntity saved = jpaRepository.save(entity);
        return entityToDomain(saved);
    }

    @Override
    public Optional<Customer> findById(Long id) {
        return jpaRepository.findById(id).map(this::entityToDomain);
    }

    @Override
    public Optional<Customer> findByCustomerCode(String customerCode) {
        return jpaRepository.findByCustomerCode(customerCode).map(this::entityToDomain);
    }

    @Override
    public Optional<Customer> findByEmail(String email) {
        return jpaRepository.findByEmail(email).map(this::entityToDomain);
    }

    @Override
    public List<Customer> findByPhone(String phone) {
        return jpaRepository.findByPhone(phone).stream()
                .map(this::entityToDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Page<Customer> findAll(Pageable pageable) {
        Page<CustomerEntity> page = jpaRepository.findAllActive(pageable);
        return page.map(this::entityToDomain);
    }

    @Override
    public Page<Customer> findByType(String type, Pageable pageable) {
        Page<CustomerEntity> page = jpaRepository.findByType(type, pageable);
        return page.map(this::entityToDomain);
    }

    @Override
    public Page<Customer> findByAssignedTo(Long userId, Pageable pageable) {
        Page<CustomerEntity> page = jpaRepository.findByAssignedTo(userId, pageable);
        return page.map(this::entityToDomain);
    }

    @Override
    public Page<Customer> findByStatusId(Long statusId, Pageable pageable) {
        Page<CustomerEntity> page = jpaRepository.findByStatusId(statusId, pageable);
        return page.map(this::entityToDomain);
    }

    @Override
    public Page<Customer> findByTierId(Long tierId, Pageable pageable) {
        Page<CustomerEntity> page = jpaRepository.findByTierId(tierId, pageable);
        return page.map(this::entityToDomain);
    }

    @Override
    public Page<Customer> findBySourceId(Long sourceId, Pageable pageable) {
        Page<CustomerEntity> page = jpaRepository.findBySourceId(sourceId, pageable);
        return page.map(this::entityToDomain);
    }

    @Override
    public Optional<Customer> findByTaxCode(String taxCode) {
        return jpaRepository.findByTaxCode(taxCode).map(this::entityToDomain);
    }

    @Override
    public void delete(Long id) {
        Optional<CustomerEntity> entity = jpaRepository.findById(id);
        entity.ifPresent(e -> {
            e.setDeletedAt(LocalDateTime.now());
            jpaRepository.save(e);
        });
    }

    @Override
    public boolean existsById(Long id) {
        return jpaRepository.existsById(id);
    }

    @Override
    public long count() {
        return jpaRepository.count();
    }

    // Helper methods
    private Customer entityToDomain(CustomerEntity entity) {
        if (entity == null) return null;

        Customer customer = new Customer();
        customer.setId(entity.getId());
        customer.setParentId(entity.getParentId());
        customer.setCustomerCode(entity.getCustomerCode());
        customer.setType(entity.getType() != null ? 
                org.example.crm_project.modules.customers.domain.constant.CustomerType.valueOf(entity.getType()) : null);
        customer.setName(entity.getName());
        customer.setShortName(entity.getShortName());
        customer.setTaxCode(entity.getTaxCode());
        customer.setPhone(entity.getPhone());
        customer.setEmail(entity.getEmail());
        customer.setFax(entity.getFax());
        customer.setEstablishedDate(entity.getEstablishedDate());
        customer.setDescription(entity.getDescription());
        customer.setSourceId(entity.getSourceId());
        customer.setStatus(entity.getStatusId() != null ? 
                org.example.crm_project.modules.customers.domain.constant.CustomerStatus.fromId(entity.getStatusId()) : null);
        customer.setTier(entity.getTierId() != null ? 
                org.example.crm_project.modules.customers.domain.constant.CustomerTier.fromId(entity.getTierId()) : null);
        customer.setAssignedTo(entity.getAssignedTo());
        customer.setCreatedBy(entity.getCreatedBy());
        customer.setUpdatedBy(entity.getUpdatedBy());
        customer.setCreatedAt(entity.getCreatedAt());
        customer.setUpdatedAt(entity.getUpdatedAt());
        customer.setDeletedAt(entity.getDeletedAt());

        return customer;
    }

    private CustomerEntity domainToEntity(Customer customer) {
        if (customer == null) return null;

        CustomerEntity entity = new CustomerEntity();
        entity.setId(customer.getId());
        entity.setParentId(customer.getParentId());
        entity.setCustomerCode(customer.getCustomerCode());
        entity.setType(customer.getType() != null ? customer.getType().getCode() : null);
        entity.setName(customer.getName());
        entity.setShortName(customer.getShortName());
        entity.setTaxCode(customer.getTaxCode());
        entity.setPhone(customer.getPhone());
        entity.setEmail(customer.getEmail());
        entity.setFax(customer.getFax());
        entity.setEstablishedDate(customer.getEstablishedDate());
        entity.setDescription(customer.getDescription());
        entity.setSourceId(customer.getSourceId());
        entity.setStatusId(customer.getStatus() != null ? customer.getStatus().getId() : null);
        entity.setTierId(customer.getTier() != null ? customer.getTier().getId() : null);
        entity.setAssignedTo(customer.getAssignedTo());
        entity.setCreatedBy(customer.getCreatedBy());
        entity.setUpdatedBy(customer.getUpdatedBy());
        entity.setCreatedAt(customer.getCreatedAt());
        entity.setUpdatedAt(customer.getUpdatedAt());
        entity.setDeletedAt(customer.getDeletedAt());

        return entity;
    }
}
