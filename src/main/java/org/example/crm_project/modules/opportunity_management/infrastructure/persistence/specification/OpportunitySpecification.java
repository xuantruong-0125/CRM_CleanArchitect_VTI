package org.example.crm_project.modules.opportunity_management.infrastructure.persistence.specification;

import org.example.crm_project.modules.opportunity_management.infrastructure.persistence.entity.OpportunityJpaEntity;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * JPA Specification – xây dựng điều kiện lọc động cho OpportunityJpaEntity.
 * Chỉ nằm ở Infrastructure layer, không có trong Domain.
 */
public class OpportunitySpecification {

    public static Specification<OpportunityJpaEntity> hasCustomerId(Integer customerId) {
        return (root, query, cb) -> customerId == null ? null : cb.equal(root.get("customerId"), customerId);
    }

    public static Specification<OpportunityJpaEntity> hasAssignedUserId(Integer assignedUserId) {
        return (root, query, cb) -> assignedUserId == null ? null : cb.equal(root.get("assignedUserId"), assignedUserId);
    }

    public static Specification<OpportunityJpaEntity> hasPipelineId(Integer pipelineId) {
        return (root, query, cb) -> pipelineId == null ? null : cb.equal(root.get("pipeline").get("id"), pipelineId);
    }

    public static Specification<OpportunityJpaEntity> hasStageId(Integer stageId) {
        return (root, query, cb) -> stageId == null ? null : cb.equal(root.get("stage").get("id"), stageId);
    }

    public static Specification<OpportunityJpaEntity> hasHealthStatus(String healthStatus) {
        return (root, query, cb) -> (healthStatus == null || healthStatus.isEmpty())
                ? null : cb.equal(root.get("healthStatus"), healthStatus);
    }

    public static Specification<OpportunityJpaEntity> closeDateBetween(LocalDate dateFrom, LocalDate dateTo) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (dateFrom != null) predicates.add(cb.greaterThanOrEqualTo(root.get("expectedCloseDate"), dateFrom));
            if (dateTo != null) predicates.add(cb.lessThanOrEqualTo(root.get("expectedCloseDate"), dateTo));
            return predicates.isEmpty() ? null : cb.and(predicates.toArray(new Predicate[0]));
        };
    }

    public static Specification<OpportunityJpaEntity> nameContains(String keyword) {
        return (root, query, cb) -> (keyword == null || keyword.trim().isEmpty())
                ? null : cb.like(cb.lower(root.get("name")), "%" + keyword.trim().toLowerCase() + "%");
    }
}
