package org.example.crm_project.modules.opportunity_management.infrastructure.repository;

import org.example.crm_project.modules.opportunity_management.domain.entity.Opportunity;
import org.example.crm_project.modules.opportunity_management.domain.repository.OpportunityRepository;
import org.example.crm_project.modules.opportunity_management.infrastructure.persistence.entity.OpportunityJpaEntity;
import org.example.crm_project.modules.opportunity_management.infrastructure.persistence.repository.JpaOpportunityRepository;
import org.example.crm_project.modules.opportunity_management.infrastructure.persistence.mapper.OpportunityEntityMapper;
import org.example.crm_project.modules.opportunity_management.infrastructure.persistence.specification.OpportunitySpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Repository Adapter – Kết nối Domain Port ↔ Spring Data JPA.
 *
 * @Component này implement interface của Domain (port), đây là nơi duy nhất
 * mà Spring/JPA đi vào để phục vụ Domain.
 */
@Component
@RequiredArgsConstructor
public class OpportunityRepositoryAdapter implements OpportunityRepository {

    private final JpaOpportunityRepository jpaRepository;
    private final OpportunityEntityMapper mapper;

    @Override
    public List<Opportunity> findAll() {
        return jpaRepository.findAll()
                .stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Opportunity> findById(Integer id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public Opportunity save(Opportunity opportunity) {
        OpportunityJpaEntity entity = mapper.toEntity(opportunity);
        OpportunityJpaEntity saved = jpaRepository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public void deleteById(Integer id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public Page<Opportunity> filter(String keyword, Integer customerId, Integer assignedUserId,
                                    Integer pipelineId, Integer stageId, String healthStatus,
                                    LocalDate dateFrom, LocalDate dateTo,
                                    String sortField, String sortDir, int page, int size) {

        Specification<OpportunityJpaEntity> spec = Specification
                .where(OpportunitySpecification.nameContains(keyword))
                .and(OpportunitySpecification.hasCustomerId(customerId))
                .and(OpportunitySpecification.hasAssignedUserId(assignedUserId))
                .and(OpportunitySpecification.hasPipelineId(pipelineId))
                .and(OpportunitySpecification.hasStageId(stageId))
                .and(OpportunitySpecification.hasHealthStatus(healthStatus))
                .and(OpportunitySpecification.closeDateBetween(dateFrom, dateTo));

        Sort sort = Sort.unsorted();
        if (sortField != null && !sortField.isEmpty()) {
            Sort.Direction direction = "desc".equalsIgnoreCase(sortDir) ? Sort.Direction.DESC : Sort.Direction.ASC;
            sort = Sort.by(direction, sortField);
        }

        Pageable pageable = PageRequest.of(page - 1, size, sort);
        return jpaRepository.findAll(spec, pageable).map(mapper::toDomain);
    }
}
