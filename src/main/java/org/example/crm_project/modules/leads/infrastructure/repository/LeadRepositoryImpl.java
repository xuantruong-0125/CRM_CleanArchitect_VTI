package org.example.crm_project.modules.leads.infrastructure.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.StoredProcedureQuery;
import lombok.RequiredArgsConstructor;
import org.example.crm_project.modules.leads.domain.entity.Lead;
import org.example.crm_project.modules.leads.domain.entity.LeadConversionResult;
import org.example.crm_project.modules.leads.domain.entity.LeadPageResult;
import org.example.crm_project.modules.leads.domain.exception.InvalidLeadException;
import org.example.crm_project.modules.leads.domain.repository.LeadRepository;
import org.example.crm_project.modules.leads.infrastructure.persistence.entity.LeadProductInterestEntity;
import org.example.crm_project.modules.leads.infrastructure.persistence.mapper.LeadJpaMapper;
import org.example.crm_project.modules.leads.infrastructure.persistence.repository.JpaLeadProductInterestRepository;
import org.example.crm_project.modules.leads.infrastructure.persistence.repository.JpaLeadRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class LeadRepositoryImpl implements LeadRepository {

    @PersistenceContext
    private EntityManager entityManager;

    private final JpaLeadRepository jpaLeadRepository;
    private final JpaLeadProductInterestRepository jpaLeadProductInterestRepository;

    @Override
    @Transactional
    public Lead save(Lead lead) {
        Lead savedLead = LeadJpaMapper.toDomain(
                jpaLeadRepository.save(LeadJpaMapper.toEntity(lead)),
                lead.getProductInterestIds()
        );

        syncProductInterests(savedLead.getId(), savedLead.getProductInterestIds());
        return findById(savedLead.getId()).orElse(savedLead);
    }

    @Override
    @Transactional(readOnly = true)
    public java.util.Optional<Lead> findById(Long id) {
        return jpaLeadRepository.findByIdAndDeletedAtIsNull(id)
                .map(entity -> LeadJpaMapper.toDomain(entity, loadProductInterestIds(entity.getId())));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Lead> findAll() {
        return jpaLeadRepository.findAllByDeletedAtIsNull().stream()
                .map(entity -> LeadJpaMapper.toDomain(entity, loadProductInterestIds(entity.getId())))
                .toList();
    }

        @Override
        @Transactional(readOnly = true)
        public LeadPageResult<Lead> findAll(int page, int size, String sortBy, String sortDir) {
        Pageable pageable = buildPageable(page, size, sortBy, sortDir);

        Page<org.example.crm_project.modules.leads.infrastructure.persistence.entity.LeadEntity> resultPage =
            jpaLeadRepository.findAll((root, query, cb) -> cb.isNull(root.get("deletedAt")), pageable);

        List<Lead> content = resultPage.getContent().stream()
            .map(entity -> LeadJpaMapper.toDomain(entity, loadProductInterestIds(entity.getId())))
            .toList();

        return toLeadPageResult(resultPage, content);
        }

    @Override
    @Transactional(readOnly = true)
    public List<Lead> search(Integer provinceId, Long organizationId, String phone, Long sourceId) {
        Specification<org.example.crm_project.modules.leads.infrastructure.persistence.entity.LeadEntity> specification =
            buildSearchSpecification(provinceId, organizationId, phone, sourceId);

        return jpaLeadRepository.findAll(specification).stream()
                .map(entity -> LeadJpaMapper.toDomain(entity, loadProductInterestIds(entity.getId())))
                .toList();
    }

        @Override
        @Transactional(readOnly = true)
        public LeadPageResult<Lead> search(
            Integer provinceId,
            Long organizationId,
            String phone,
            Long sourceId,
            int page,
            int size,
            String sortBy,
            String sortDir
        ) {
        Specification<org.example.crm_project.modules.leads.infrastructure.persistence.entity.LeadEntity> specification =
            buildSearchSpecification(provinceId, organizationId, phone, sourceId);
        Pageable pageable = buildPageable(page, size, sortBy, sortDir);

        Page<org.example.crm_project.modules.leads.infrastructure.persistence.entity.LeadEntity> resultPage =
            jpaLeadRepository.findAll(specification, pageable);

        List<Lead> content = resultPage.getContent().stream()
            .map(entity -> LeadJpaMapper.toDomain(entity, loadProductInterestIds(entity.getId())))
            .toList();

        return toLeadPageResult(resultPage, content);
        }

    @Override
    @Transactional
    public LeadConversionResult convert(Long leadId, Long userId) {
        try {
            StoredProcedureQuery storedProcedureQuery = entityManager
                    .createStoredProcedureQuery("sp_ConvertLeadToCustomer")
                    .registerStoredProcedureParameter(1, Long.class, ParameterMode.IN)
                    .registerStoredProcedureParameter(2, Long.class, ParameterMode.IN)
                    .registerStoredProcedureParameter(3, Long.class, ParameterMode.OUT)
                    .registerStoredProcedureParameter(4, Long.class, ParameterMode.OUT)
                    .registerStoredProcedureParameter(5, Long.class, ParameterMode.OUT);

            storedProcedureQuery.setParameter(1, leadId);
            storedProcedureQuery.setParameter(2, userId);
            storedProcedureQuery.execute();

            Long customerId = toLong(storedProcedureQuery.getOutputParameterValue(3));
            Long contactId = toLong(storedProcedureQuery.getOutputParameterValue(4));
            Long opportunityId = toLong(storedProcedureQuery.getOutputParameterValue(5));

            return LeadConversionResult.builder()
                    .customerId(customerId)
                    .contactId(contactId)
                    .opportunityId(opportunityId)
                    .build();
        } catch (Exception ex) {
            throw new InvalidLeadException("Lead conversion failed: " + ex.getMessage());
        }
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Lead lead = findById(id).orElse(null);
        if (lead == null) {
            return;
        }

        lead.setDeletedAt(LocalDateTime.now());
        lead.setUpdatedAt(LocalDateTime.now());
        jpaLeadRepository.save(LeadJpaMapper.toEntity(lead));
        jpaLeadProductInterestRepository.deleteAllByLeadId(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(Long id) {
        return jpaLeadRepository.existsByIdAndDeletedAtIsNull(id);
    }

    private void syncProductInterests(Long leadId, List<Long> productInterestIds) {
        jpaLeadProductInterestRepository.deleteAllByLeadId(leadId);

        if (productInterestIds == null || productInterestIds.isEmpty()) {
            return;
        }

        List<LeadProductInterestEntity> entities = productInterestIds.stream()
                .distinct()
                .map(productId -> {
                    LeadProductInterestEntity entity = new LeadProductInterestEntity();
                    entity.setLeadId(leadId);
                    entity.setProductId(productId);
                    return entity;
                })
                .toList();

        jpaLeadProductInterestRepository.saveAll(entities);
    }

    private List<Long> loadProductInterestIds(Long leadId) {
        return jpaLeadProductInterestRepository.findAllByLeadId(leadId).stream()
                .map(LeadProductInterestEntity::getProductId)
                .toList();
    }

    private Long toLong(Object value) {
        if (value == null) {
            return null;
        }
        return ((Number) value).longValue();
    }

    private Specification<org.example.crm_project.modules.leads.infrastructure.persistence.entity.LeadEntity> buildSearchSpecification(
            Integer provinceId,
            Long organizationId,
            String phone,
            Long sourceId
    ) {
        Specification<org.example.crm_project.modules.leads.infrastructure.persistence.entity.LeadEntity> specification =
                (root, query, cb) -> cb.isNull(root.get("deletedAt"));

        if (provinceId != null) {
            specification = specification.and((root, query, cb) -> cb.equal(root.get("provinceId"), provinceId));
        }

        if (organizationId != null) {
            specification = specification.and((root, query, cb) -> cb.equal(root.get("organizationId"), organizationId));
        }

        if (sourceId != null) {
            specification = specification.and((root, query, cb) -> cb.equal(root.get("sourceId"), sourceId));
        }

        if (StringUtils.hasText(phone)) {
            String normalizedPhone = "%" + phone.trim().toLowerCase() + "%";
            specification = specification.and((root, query, cb) -> cb.like(cb.lower(root.get("phone")), normalizedPhone));
        }

        return specification;
    }

    private Pageable buildPageable(int page, int size, String sortBy, String sortDir) {
        Sort.Direction direction = "asc".equalsIgnoreCase(sortDir) ? Sort.Direction.ASC : Sort.Direction.DESC;
        return PageRequest.of(page, size, Sort.by(direction, sortBy));
    }

    private LeadPageResult<Lead> toLeadPageResult(
            Page<org.example.crm_project.modules.leads.infrastructure.persistence.entity.LeadEntity> page,
            List<Lead> content
    ) {
        return LeadPageResult.<Lead>builder()
                .content(content)
                .page(page.getNumber())
                .size(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .hasNext(page.hasNext())
                .hasPrevious(page.hasPrevious())
                .build();
    }
}