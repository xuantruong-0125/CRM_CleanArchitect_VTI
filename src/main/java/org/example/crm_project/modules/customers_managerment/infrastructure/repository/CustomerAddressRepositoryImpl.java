package org.example.crm_project.modules.customers_managerment.infrastructure.repository;

import org.example.crm_project.modules.customers_managerment.domain.entity.CustomerAddress;
import org.example.crm_project.modules.customers_managerment.domain.repository.CustomerAddressRepository;
import org.example.crm_project.modules.customers_managerment.infrastructure.persistence.entity.CustomerAddressEntity;
import org.example.crm_project.modules.customers_managerment.infrastructure.persistence.jpa.CustomerAddressJpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Repository Implementation: CustomerAddressRepositoryImpl
 */
@Repository
public class CustomerAddressRepositoryImpl implements CustomerAddressRepository {

    private final CustomerAddressJpaRepository jpaRepository;

    public CustomerAddressRepositoryImpl(CustomerAddressJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public CustomerAddress save(CustomerAddress customerAddress) {
        CustomerAddressEntity entity = domainToEntity(customerAddress);
        CustomerAddressEntity saved = jpaRepository.save(entity);
        return entityToDomain(saved);
    }

    @Override
    public Optional<CustomerAddress> findById(Long id) {
        return jpaRepository.findById(id).map(this::entityToDomain);
    }

    @Override
    public List<CustomerAddress> findByCustomerId(Long customerId) {
        return jpaRepository.findByCustomerId(customerId).stream()
                .map(this::entityToDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Page<CustomerAddress> findByCustomerId(Long customerId, Pageable pageable) {
        Page<CustomerAddressEntity> page = jpaRepository.findByCustomerId(customerId, pageable);
        return page.map(this::entityToDomain);
    }

    @Override
    public Optional<CustomerAddress> findPrimaryAddress(Long customerId) {
        return jpaRepository.findPrimaryAddress(customerId).map(this::entityToDomain);
    }

    @Override
    public void delete(Long id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public void deleteByCustomerId(Long customerId) {
        jpaRepository.deleteByCustomerId(customerId);
    }

    @Override
    public boolean existsById(Long id) {
        return jpaRepository.existsById(id);
    }

    @Override
    public long count() {
        return jpaRepository.count();
    }

    private CustomerAddress entityToDomain(CustomerAddressEntity entity) {
        if (entity == null) return null;

        CustomerAddress customerAddress = new CustomerAddress();
        customerAddress.setId(entity.getId());
        customerAddress.setCustomerId(entity.getCustomerId());
        customerAddress.setAddressType(entity.getAddressType());
        customerAddress.setFullAddress(entity.getFullAddress());
        customerAddress.setProvinceId(entity.getProvinceId());
        customerAddress.setIsPrimary(entity.getIsPrimary());

        return customerAddress;
    }

    private CustomerAddressEntity domainToEntity(CustomerAddress customerAddress) {
        if (customerAddress == null) return null;

        CustomerAddressEntity entity = new CustomerAddressEntity();
        entity.setId(customerAddress.getId());
        entity.setCustomerId(customerAddress.getCustomerId());
        entity.setAddressType(customerAddress.getAddressType());
        entity.setFullAddress(customerAddress.getFullAddress());
        entity.setProvinceId(customerAddress.getProvinceId());
        entity.setIsPrimary(customerAddress.getIsPrimary());

        return entity;
    }
}
