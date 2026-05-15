package org.example.crm_project.modules.kpi_management.infrastructure.persistence.repository;

import org.example.crm_project.modules.kpi_management.infrastructure.persistence.entity.KpiConfigEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JpaKpiConfigRepository extends JpaRepository<KpiConfigEntity, Integer> {
    Page<KpiConfigEntity> findAllByDeletedAtIsNull(Pageable pageable);
    Page<KpiConfigEntity> findAllByNameContainingIgnoreCaseAndDeletedAtIsNull(String name, Pageable pageable);
    Optional<KpiConfigEntity> findByIdAndDeletedAtIsNull(Integer id);

    @org.springframework.data.jpa.repository.Query("SELECT DISTINCT c FROM KpiConfigEntity c " +
           "JOIN c.assignments a " +
           "WHERE (a.userId = :userId OR a.organizationId = :organizationId) " +
           "AND c.deletedAt IS NULL")
    java.util.List<KpiConfigEntity> findAssignedConfigs(
            @org.springframework.data.repository.query.Param("userId") Integer userId, 
            @org.springframework.data.repository.query.Param("organizationId") Integer organizationId);
}
