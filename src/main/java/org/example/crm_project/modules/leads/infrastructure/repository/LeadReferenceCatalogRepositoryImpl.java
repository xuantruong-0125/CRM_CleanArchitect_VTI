package org.example.crm_project.modules.leads.infrastructure.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.example.crm_project.modules.leads.domain.entity.LeadReferenceOption;
import org.example.crm_project.modules.leads.domain.repository.LeadReferenceCatalogRepository;
import org.springframework.stereotype.Repository;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Repository
public class LeadReferenceCatalogRepositoryImpl implements LeadReferenceCatalogRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<LeadReferenceOption> findLeadStatuses() {
        List<?> rows = entityManager.createNativeQuery("""
                SELECT id, code, name
                FROM sys_lead_statuses
                ORDER BY id
                """).getResultList();

        return mapOptions(rows);
    }

    @Override
    public List<LeadReferenceOption> findLeadSources() {
        List<?> rows = entityManager.createNativeQuery("""
                SELECT id, NULL AS code, name
                FROM sys_lead_sources
                ORDER BY id
                """).getResultList();

        return mapOptions(rows);
    }

    @Override
    public List<LeadReferenceOption> findProvinces() {
        List<?> rows = entityManager.createNativeQuery("""
                SELECT id, code, name
                FROM provinces
                ORDER BY name
                """).getResultList();

        return mapOptions(rows);
    }

    @Override
    public List<LeadReferenceOption> findOrganizations() {
        List<?> rows = entityManager.createNativeQuery("""
                SELECT id, NULL AS code, name
                FROM organizations
                ORDER BY name
                """).getResultList();

        return mapOptions(rows);
    }

    @Override
    public List<LeadReferenceOption> findProducts() {
        List<?> rows = entityManager.createNativeQuery("""
                SELECT id, sku_code AS code, name
                FROM products
                WHERE is_active = 1
                  AND deleted_at IS NULL
                ORDER BY name
                """).getResultList();

        return mapOptions(rows);
    }

    @Override
    public List<LeadReferenceOption> searchAssignees(String q,
                                                     Long organizationId,
                                                     Long roleId,
                                                     String status,
                                                     int page,
                                                     int size,
                                                     String sortBy,
                                                     String sortDir) {
        String orderBy = switch (sortBy) {
            case "username" -> "u.username";
            case "createdAt" -> "u.created_at";
            default -> "u.full_name";
        };

        StringBuilder sql = new StringBuilder("""
                SELECT u.id, u.username AS code, COALESCE(u.full_name, u.username) AS name
                FROM users u
                WHERE u.deleted_at IS NULL
                """);

        Map<String, Object> params = new LinkedHashMap<>();

        if (hasText(q)) {
            sql.append(" AND (u.full_name LIKE :q OR u.username LIKE :q OR u.email LIKE :q) ");
            params.put("q", "%" + q.trim() + "%");
        }
        if (organizationId != null) {
            sql.append(" AND u.organization_id = :organizationId ");
            params.put("organizationId", organizationId);
        }
        if (roleId != null) {
            sql.append(" AND u.role_id = :roleId ");
            params.put("roleId", roleId);
        }
        if (hasText(status)) {
            sql.append(" AND u.status = :status ");
            params.put("status", status.trim().toUpperCase());
        }

        sql.append(" ORDER BY ").append(orderBy).append(" ").append(normalizeSortDir(sortDir));

        Query query = entityManager.createNativeQuery(sql.toString());
        bindParams(query, params);
        query.setFirstResult(page * size);
        query.setMaxResults(size);

        return mapOptions(query.getResultList());
    }

    @Override
    public long countAssignees(String q, Long organizationId, Long roleId, String status) {
        StringBuilder sql = new StringBuilder("""
                SELECT COUNT(*)
                FROM users u
                WHERE u.deleted_at IS NULL
                """);

        Map<String, Object> params = new LinkedHashMap<>();

        if (hasText(q)) {
            sql.append(" AND (u.full_name LIKE :q OR u.username LIKE :q OR u.email LIKE :q) ");
            params.put("q", "%" + q.trim() + "%");
        }
        if (organizationId != null) {
            sql.append(" AND u.organization_id = :organizationId ");
            params.put("organizationId", organizationId);
        }
        if (roleId != null) {
            sql.append(" AND u.role_id = :roleId ");
            params.put("roleId", roleId);
        }
        if (hasText(status)) {
            sql.append(" AND u.status = :status ");
            params.put("status", status.trim().toUpperCase());
        }

        Query query = entityManager.createNativeQuery(sql.toString());
        bindParams(query, params);
        return ((Number) query.getSingleResult()).longValue();
    }

    @Override
    public List<LeadReferenceOption> searchProducts(String q,
                                                    String type,
                                                    Long categoryId,
                                                    Boolean isActive,
                                                    int page,
                                                    int size,
                                                    String sortBy,
                                                    String sortDir) {
        String orderBy = switch (sortBy) {
            case "skuCode" -> "p.sku_code";
            case "createdAt" -> "p.created_at";
            default -> "p.name";
        };

        StringBuilder sql = new StringBuilder("""
                SELECT p.id, p.sku_code AS code, p.name
                FROM products p
                WHERE p.deleted_at IS NULL
                """);

        Map<String, Object> params = new LinkedHashMap<>();

        if (hasText(q)) {
            sql.append(" AND (p.name LIKE :q OR p.sku_code LIKE :q) ");
            params.put("q", "%" + q.trim() + "%");
        }
        if (hasText(type)) {
            sql.append(" AND p.type = :type ");
            params.put("type", type.trim().toUpperCase());
        }
        if (categoryId != null) {
            sql.append(" AND p.category_id = :categoryId ");
            params.put("categoryId", categoryId);
        }
        if (isActive != null) {
            sql.append(" AND p.is_active = :isActive ");
            params.put("isActive", isActive ? 1 : 0);
        }

        sql.append(" ORDER BY ").append(orderBy).append(" ").append(normalizeSortDir(sortDir));

        Query query = entityManager.createNativeQuery(sql.toString());
        bindParams(query, params);
        query.setFirstResult(page * size);
        query.setMaxResults(size);

        return mapOptions(query.getResultList());
    }

    @Override
    public long countProducts(String q, String type, Long categoryId, Boolean isActive) {
        StringBuilder sql = new StringBuilder("""
                SELECT COUNT(*)
                FROM products p
                WHERE p.deleted_at IS NULL
                """);

        Map<String, Object> params = new LinkedHashMap<>();

        if (hasText(q)) {
            sql.append(" AND (p.name LIKE :q OR p.sku_code LIKE :q) ");
            params.put("q", "%" + q.trim() + "%");
        }
        if (hasText(type)) {
            sql.append(" AND p.type = :type ");
            params.put("type", type.trim().toUpperCase());
        }
        if (categoryId != null) {
            sql.append(" AND p.category_id = :categoryId ");
            params.put("categoryId", categoryId);
        }
        if (isActive != null) {
            sql.append(" AND p.is_active = :isActive ");
            params.put("isActive", isActive ? 1 : 0);
        }

        Query query = entityManager.createNativeQuery(sql.toString());
        bindParams(query, params);
        return ((Number) query.getSingleResult()).longValue();
    }

    @Override
    public List<LeadReferenceOption> searchProvinces(String q,
                                                     String code,
                                                     int page,
                                                     int size,
                                                     String sortBy,
                                                     String sortDir) {
        String orderBy = switch (sortBy) {
            case "code" -> "p.code";
            case "id" -> "p.id";
            default -> "p.name";
        };

        StringBuilder sql = new StringBuilder("""
                SELECT p.id, p.code, p.name
                FROM provinces p
                WHERE 1 = 1
                """);

        Map<String, Object> params = new LinkedHashMap<>();

        if (hasText(q)) {
            sql.append(" AND (p.name LIKE :q OR p.code LIKE :q) ");
            params.put("q", "%" + q.trim() + "%");
        }
        if (hasText(code)) {
            sql.append(" AND p.code = :code ");
            params.put("code", code.trim().toUpperCase());
        }

        sql.append(" ORDER BY ").append(orderBy).append(" ").append(normalizeSortDir(sortDir));

        Query query = entityManager.createNativeQuery(sql.toString());
        bindParams(query, params);
        query.setFirstResult(page * size);
        query.setMaxResults(size);

        return mapOptions(query.getResultList());
    }

    @Override
    public long countProvinces(String q, String code) {
        StringBuilder sql = new StringBuilder("""
                SELECT COUNT(*)
                FROM provinces p
                WHERE 1 = 1
                """);

        Map<String, Object> params = new LinkedHashMap<>();

        if (hasText(q)) {
            sql.append(" AND (p.name LIKE :q OR p.code LIKE :q) ");
            params.put("q", "%" + q.trim() + "%");
        }
        if (hasText(code)) {
            sql.append(" AND p.code = :code ");
            params.put("code", code.trim().toUpperCase());
        }

        Query query = entityManager.createNativeQuery(sql.toString());
        bindParams(query, params);
        return ((Number) query.getSingleResult()).longValue();
    }

    private List<LeadReferenceOption> mapOptions(List<?> rows) {
        return rows.stream()
                .map(row -> (Object[]) row)
                .map(columns -> LeadReferenceOption.builder()
                        .id(toLong(columns[0]))
                        .code(columns[1] == null ? null : columns[1].toString())
                        .name(columns[2] == null ? null : columns[2].toString())
                        .build())
                .toList();
    }

    private Long toLong(Object value) {
        if (value == null) {
            return null;
        }
        return ((Number) value).longValue();
    }

    private void bindParams(Query query, Map<String, Object> params) {
        params.forEach(query::setParameter);
    }

    private boolean hasText(String value) {
        return value != null && !value.trim().isEmpty();
    }

    private String normalizeSortDir(String sortDir) {
        if (sortDir == null) {
            return "asc";
        }

        String value = sortDir.trim().toLowerCase();
        return value.equals("desc") ? "desc" : "asc";
    }
}