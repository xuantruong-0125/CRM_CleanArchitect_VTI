package org.example.crm_project.modules.customers.infrastructure.repository;

import org.example.crm_project.modules.customers.domain.entity.Opportunity;
import org.example.crm_project.modules.customers.domain.repository.OpportunityRepository;
import org.example.crm_project.modules.customers.infrastructure.persistence.entity.OpportunityEntity;
import org.example.crm_project.modules.customers.infrastructure.persistence.jpa.OpportunityJpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Repository Implementation: OpportunityRepositoryImpl
 */
@Repository
public class OpportunityRepositoryImpl implements OpportunityRepository {

    private final OpportunityJpaRepository jpaRepository;

    public OpportunityRepositoryImpl(OpportunityJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Opportunity save(Opportunity opportunity) {
        OpportunityEntity entity = domainToEntity(opportunity);
        OpportunityEntity saved = jpaRepository.save(entity);
        return entityToDomain(saved);
    }

    @Override
    public Optional<Opportunity> findById(Long id) {
        return jpaRepository.findById(id).map(this::entityToDomain);
    }

    @Override
    public Page<Opportunity> findByCustomerId(Long customerId, Pageable pageable) {
        Page<OpportunityEntity> page = jpaRepository.findByCustomerId(customerId, pageable);
        return page.map(this::entityToDomain);
    }

    @Override
    public Page<Opportunity> findByAssignedUserId(Long userId, Pageable pageable) {
        Page<OpportunityEntity> page = jpaRepository.findByAssignedUserId(userId, pageable);
        return page.map(this::entityToDomain);
    }

    @Override
    public Page<Opportunity> findByHealthStatus(String healthStatus, Pageable pageable) {
        Page<OpportunityEntity> page = jpaRepository.findByHealthStatus(healthStatus, pageable);
        return page.map(this::entityToDomain);
    }

    @Override
    public java.util.List<Opportunity> findAll() {
        return jpaRepository.findAll().stream()
                .map(this::entityToDomain)
                .collect(java.util.stream.Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        Optional<OpportunityEntity> entity = jpaRepository.findById(id);
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

    private Opportunity entityToDomain(OpportunityEntity entity) {
        if (entity == null) return null;

        Opportunity opportunity = new Opportunity();
        opportunity.setId(entity.getId());
        opportunity.setName(entity.getName());
        opportunity.setCustomerId(entity.getCustomerId());
        opportunity.setPipelineId(entity.getPipelineId());
        opportunity.setStageId(entity.getStageId());
        opportunity.setTotalAmount(entity.getTotalAmount());
        opportunity.setDepositAmount(entity.getDepositAmount());
        opportunity.setRemainingAmount(entity.getRemainingAmount());
        opportunity.setCurrencyCode(entity.getCurrencyCode());
        opportunity.setExchangeRate(entity.getExchangeRate());
        opportunity.setExpectedCloseDate(entity.getExpectedCloseDate());
        opportunity.setLossReasonId(entity.getLossReasonId());
        opportunity.setHealthStatus(entity.getHealthStatus());
        opportunity.setAssignedUserId(entity.getAssignedUserId());
        opportunity.setCreatedBy(entity.getCreatedBy());
        opportunity.setUpdatedBy(entity.getUpdatedBy());
        opportunity.setCreatedAt(entity.getCreatedAt());
        opportunity.setUpdatedAt(entity.getUpdatedAt());
        opportunity.setDeletedAt(entity.getDeletedAt());

        return opportunity;
    }

    private OpportunityEntity domainToEntity(Opportunity opportunity) {
        if (opportunity == null) return null;

        OpportunityEntity entity = new OpportunityEntity();
        entity.setId(opportunity.getId());
        entity.setName(opportunity.getName());
        entity.setCustomerId(opportunity.getCustomerId());
        entity.setPipelineId(opportunity.getPipelineId());
        entity.setStageId(opportunity.getStageId());
        entity.setTotalAmount(opportunity.getTotalAmount());
        entity.setDepositAmount(opportunity.getDepositAmount());
        entity.setRemainingAmount(opportunity.getRemainingAmount());
        entity.setCurrencyCode(opportunity.getCurrencyCode());
        entity.setExchangeRate(opportunity.getExchangeRate());
        entity.setExpectedCloseDate(opportunity.getExpectedCloseDate());
        entity.setLossReasonId(opportunity.getLossReasonId());
        entity.setHealthStatus(opportunity.getHealthStatus());
        entity.setAssignedUserId(opportunity.getAssignedUserId());
        entity.setCreatedBy(opportunity.getCreatedBy());
        entity.setUpdatedBy(opportunity.getUpdatedBy());
        entity.setCreatedAt(opportunity.getCreatedAt());
        entity.setUpdatedAt(opportunity.getUpdatedAt());
        entity.setDeletedAt(opportunity.getDeletedAt());

        return entity;
    }
}
