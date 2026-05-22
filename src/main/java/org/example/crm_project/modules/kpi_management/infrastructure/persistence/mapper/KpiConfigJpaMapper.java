package org.example.crm_project.modules.kpi_management.infrastructure.persistence.mapper;

import org.example.crm_project.modules.kpi_management.domain.constant.KpiStatus;
import org.example.crm_project.modules.kpi_management.domain.entity.KpiAssignment;
import org.example.crm_project.modules.kpi_management.domain.entity.KpiConfig;
import org.example.crm_project.modules.kpi_management.domain.entity.KpiTarget;
import org.example.crm_project.modules.kpi_management.infrastructure.persistence.entity.KpiAssignmentEntity;
import org.example.crm_project.modules.kpi_management.infrastructure.persistence.entity.KpiConfigEntity;
import org.example.crm_project.modules.kpi_management.infrastructure.persistence.entity.KpiTargetEntity;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class KpiConfigJpaMapper {

    public KpiConfig toDomain(KpiConfigEntity entity) {
        if (entity == null)
            return null;
        KpiConfig domain = new KpiConfig();
        domain.setId(entity.getId());
        domain.setName(entity.getName());
        domain.setStartDate(entity.getStartDate());
        domain.setEndDate(entity.getEndDate());
        domain.setStatus(entity.getStatus() != null ? entity.getStatus().name() : null);
        domain.setDescription(entity.getDescription());
        domain.setCreatedBy(entity.getCreatedBy());
        domain.setUpdatedBy(entity.getUpdatedBy());
        domain.setCreatedAt(entity.getCreatedAt());
        domain.setUpdatedAt(entity.getUpdatedAt());
        domain.setDeletedAt(entity.getDeletedAt());
        
        domain.setTargets(toTargetDomains(entity.getTargets()));
        domain.setAssignments(toAssignmentDomains(entity.getAssignments()));
        
        return domain;
    }

    private List<KpiTarget> toTargetDomains(List<KpiTargetEntity> entities) {
        if (entities == null) return Collections.emptyList();
        return entities.stream().map(e -> {
            KpiTarget d = new KpiTarget();
            d.setId(e.getId());
            d.setKpiConfigId(e.getKpiConfig() != null ? e.getKpiConfig().getId() : null);
            d.setMetricType(e.getMetricType());
            d.setTargetValue(e.getTargetValue());
            return d;
        }).collect(Collectors.toList());
    }

    private List<KpiAssignment> toAssignmentDomains(List<KpiAssignmentEntity> entities) {
        if (entities == null) return Collections.emptyList();
        return entities.stream().map(e -> {
            KpiAssignment d = new KpiAssignment();
            d.setId(e.getId());
            d.setKpiConfigId(e.getKpiConfig() != null ? e.getKpiConfig().getId() : null);
            d.setUserId(e.getUserId());
            d.setOrganizationId(e.getOrganizationId());
            d.setCommissionPercent(e.getCommissionPercent());
            return d;
        }).collect(Collectors.toList());
    }

    public KpiConfigEntity toEntity(KpiConfig domain) {
        if (domain == null) return null;
        KpiConfigEntity entity = new KpiConfigEntity();
        entity.setId(domain.getId());
        entity.setName(domain.getName());
        entity.setStartDate(domain.getStartDate());
        entity.setEndDate(domain.getEndDate());
        try {
            entity.setStatus(domain.getStatus() != null ? KpiStatus.valueOf(domain.getStatus().toUpperCase()) : KpiStatus.ACTIVE);
        } catch (Exception e) {
            entity.setStatus(KpiStatus.ACTIVE);
        }
        entity.setDescription(domain.getDescription());
        entity.setCreatedBy(domain.getCreatedBy());
        entity.setUpdatedBy(domain.getUpdatedBy());
        entity.setDeletedAt(domain.getDeletedAt());

        if (domain.getTargets() != null) {
            entity.setTargets(domain.getTargets().stream()
                    .map(t -> toTargetEntity(t, entity))
                    .collect(Collectors.toList()));
        }

        if (domain.getAssignments() != null) {
            entity.setAssignments(domain.getAssignments().stream()
                    .map(a -> toAssignmentEntity(a, entity))
                    .collect(Collectors.toList()));
        }

        return entity;
    }

    private KpiTargetEntity toTargetEntity(KpiTarget domain, KpiConfigEntity configEntity) {
        if (domain == null) return null;
        KpiTargetEntity entity = new KpiTargetEntity();
        entity.setId(domain.getId());
        entity.setKpiConfig(configEntity);
        entity.setMetricType(domain.getMetricType());
        entity.setTargetValue(domain.getTargetValue());
        return entity;
    }

    private KpiAssignmentEntity toAssignmentEntity(KpiAssignment domain, KpiConfigEntity configEntity) {
        if (domain == null) return null;
        KpiAssignmentEntity entity = new KpiAssignmentEntity();
        entity.setId(domain.getId());
        entity.setKpiConfig(configEntity);
        entity.setUserId(domain.getUserId());
        entity.setOrganizationId(domain.getOrganizationId());
        entity.setCommissionPercent(domain.getCommissionPercent());
        return entity;
    }
}
