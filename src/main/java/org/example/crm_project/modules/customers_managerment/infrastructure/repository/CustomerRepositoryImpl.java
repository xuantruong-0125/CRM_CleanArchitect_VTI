package org.example.crm_project.modules.customers_managerment.infrastructure.repository;

import org.example.crm_project.modules.customers_managerment.domain.entity.Customer;
import org.example.crm_project.modules.customers_managerment.domain.repository.CustomerRepository;
import org.example.crm_project.modules.customers_managerment.infrastructure.persistence.entity.CustomerEntity;
import org.example.crm_project.modules.customers_managerment.infrastructure.persistence.jpa.CustomerJpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;

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
    public Page<Customer> findAll(Long currentUserId, Long userOrganizationId, String scope, Pageable pageable) {
        Specification<CustomerEntity> spec = buildSearchSpecification(null, null, null, null, null, null, null, currentUserId, userOrganizationId, scope);
        Page<CustomerEntity> page = jpaRepository.findAll(spec, pageable);
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
    public List<Customer> findByIds(Collection<Long> ids) {
        if (ids == null || ids.isEmpty()) return List.of();
        return jpaRepository.findByIdIn(ids).stream()
                .map(this::entityToDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Customer> findAllActive() {
        return jpaRepository.findAllActiveList().stream()
                .map(this::entityToDomain)
                .collect(Collectors.toList());
    }

    @Override
    public long count() {
        return jpaRepository.countByDeletedAtIsNull();
    }

    @Override
    public List<Customer> searchCustomers(String keyword, int limit) {
        if (keyword == null || keyword.isBlank()) return List.of();
        return jpaRepository.searchByKeyword(keyword.trim(), PageRequest.of(0, limit)).stream()
                .map(this::entityToDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Page<Customer> search(String keyword, String type, Long statusId, Long tierId, String email, String phone, Long assignedTo, Long currentUserId, Long userOrganizationId, String scope, Pageable pageable) {
        Specification<CustomerEntity> spec = buildSearchSpecification(keyword, type, statusId, tierId, email, phone, assignedTo, currentUserId, userOrganizationId, scope);
        return jpaRepository.findAll(spec, pageable).map(this::entityToDomain);
    }

    private Specification<CustomerEntity> buildSearchSpecification(
            String keyword, String type, Long statusId, Long tierId, String email, String phone, Long assignedTo,
            Long currentUserId, Long userOrganizationId, String scope
    ) {
        Specification<CustomerEntity> spec = (root, query, cb) -> cb.isNull(root.get("deletedAt"));

        if (StringUtils.hasText(keyword)) {
            String kw = "%" + keyword.trim().toLowerCase() + "%";
            spec = spec.and((root, query, cb) -> cb.or(
                    cb.like(cb.lower(root.get("name")), kw),
                    cb.like(cb.lower(root.get("customerCode")), kw),
                    cb.like(cb.lower(root.get("taxCode")), kw),
                    cb.like(cb.lower(root.get("email")), kw),
                    cb.like(cb.lower(root.get("phone")), kw)
            ));
        }

        if (StringUtils.hasText(type)) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("type"), type));
        }
        if (statusId != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("statusId"), statusId));
        }
        if (tierId != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("tierId"), tierId));
        }
        if (assignedTo != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("assignedTo"), assignedTo));
        }
        if (StringUtils.hasText(email)) {
            String normalizedEmail = "%" + email.trim().toLowerCase() + "%";
            spec = spec.and((root, query, cb) -> cb.like(cb.lower(root.get("email")), normalizedEmail));
        }
        if (StringUtils.hasText(phone)) {
            String normalizedPhone = "%" + phone.trim().toLowerCase() + "%";
            spec = spec.and((root, query, cb) -> cb.like(cb.lower(root.get("phone")), normalizedPhone));
        }

        // Apply row-level security scope filters
        if (StringUtils.hasText(scope)) {
            if ("OWN".equalsIgnoreCase(scope) && currentUserId != null) {
                spec = spec.and((root, query, cb) ->
                        cb.or(
                                cb.equal(root.get("assignedTo"), currentUserId),
                                cb.equal(root.get("createdBy"), currentUserId)
                        )
                );
            } else if (("BRANCH".equalsIgnoreCase(scope) || "DEPARTMENT".equalsIgnoreCase(scope) || "TEAM".equalsIgnoreCase(scope)) && userOrganizationId != null) {
                spec = spec.and((root, query, cb) -> {
                    Subquery<Long> subquery = query.subquery(Long.class);
                    Root<org.example.crm_project.modules.system_managerment.infrastructure.persistence.entity.UserEntity> userRoot =
                            subquery.from(org.example.crm_project.modules.system_managerment.infrastructure.persistence.entity.UserEntity.class);
                    subquery.select(userRoot.get("id"))
                            .where(
                                    cb.equal(userRoot.get("organizationId"), userOrganizationId),
                                    cb.isNull(userRoot.get("deletedAt"))
                            );

                    return cb.or(
                            root.get("assignedTo").in(subquery),
                            cb.and(
                                    cb.isNull(root.get("assignedTo")),
                                    root.get("createdBy").in(subquery)
                            )
                    );
                });
            }
        }

        return spec;
    }

    // Helper methods
    private Customer entityToDomain(CustomerEntity entity) {
        if (entity == null) return null;

        Customer customer = new Customer();
        customer.setId(entity.getId());
        customer.setParentId(entity.getParentId());
        customer.setCustomerCode(entity.getCustomerCode());
        customer.setType(entity.getType() != null ? 
                org.example.crm_project.modules.customers_managerment.domain.constant.CustomerType.valueOf(entity.getType()) : null);
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
                org.example.crm_project.modules.customers_managerment.domain.constant.CustomerStatus.fromId(entity.getStatusId()) : null);
        customer.setTier(entity.getTierId() != null ? 
                org.example.crm_project.modules.customers_managerment.domain.constant.CustomerTier.fromId(entity.getTierId()) : null);
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
