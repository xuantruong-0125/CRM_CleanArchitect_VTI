package org.example.crm_project.modules.leads.infrastructure.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.example.crm_project.modules.leads.domain.repository.LeadReferenceRepository;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class LeadReferenceRepositoryImpl implements LeadReferenceRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public boolean existsLeadStatusById(Long statusId) {
        return existsById("sys_lead_statuses", "id", statusId);
    }

    @Override
    public String findLeadStatusCodeById(Long statusId) {
        if (statusId == null) {
            return null;
        }

        Object code = entityManager.createNativeQuery("SELECT code FROM sys_lead_statuses WHERE id = :id")
                .setParameter("id", statusId)
                .getResultStream()
                .findFirst()
                .orElse(null);

        return code == null ? null : code.toString().toUpperCase(Locale.ROOT);
    }

    @Override
    public boolean existsLeadSourceById(Long sourceId) {
        return existsById("sys_lead_sources", "id", sourceId);
    }

    @Override
    public boolean existsProvinceById(Integer provinceId) {
        return existsById("provinces", "id", provinceId);
    }

    @Override
    public boolean existsOrganizationById(Long organizationId) {
        return existsById("organizations", "id", organizationId);
    }

    @Override
    public boolean existsCampaignById(Long campaignId) {
        return existsById("campaigns", "id", campaignId);
    }

    @Override
    public boolean existsUserById(Long userId) {
        return existsById("users", "id", userId);
    }

    @Override
    public Set<Long> findExistingActiveProductIds(List<Long> productIds) {
        if (productIds == null || productIds.isEmpty()) {
            return Collections.emptySet();
        }

        List<Long> normalizedIds = productIds.stream()
                .filter(Objects::nonNull)
                .distinct()
                .toList();

        if (normalizedIds.isEmpty()) {
            return Collections.emptySet();
        }

        List<?> rawResults = entityManager.createNativeQuery("""
                SELECT id
                FROM products
                WHERE id IN (:ids)
                  AND is_active = 1
                  AND deleted_at IS NULL
                """)
                .setParameter("ids", normalizedIds)
            .getResultList();

        return rawResults
                .stream()
            .map(value -> ((Number) value).longValue())
                .collect(Collectors.toSet());
    }

    private boolean existsById(String tableName, String idColumn, Number idValue) {
        if (idValue == null) {
            return false;
        }

        Number count = (Number) entityManager.createNativeQuery(
                        "SELECT COUNT(1) FROM " + tableName + " WHERE " + idColumn + " = :id")
                .setParameter("id", idValue)
                .getSingleResult();

        return count.longValue() > 0;
    }
}