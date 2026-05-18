package org.example.crm_project.modules.kpi_management.application.mapper;

import org.example.crm_project.modules.kpi_management.application.dto.request.KpiAssignmentRequest;
import org.example.crm_project.modules.kpi_management.application.dto.request.KpiConfigRequest;
import org.example.crm_project.modules.kpi_management.application.dto.request.KpiTargetRequest;
import org.example.crm_project.modules.kpi_management.application.dto.response.KpiAssignmentResponse;
import org.example.crm_project.modules.kpi_management.application.dto.response.KpiConfigResponse;
import org.example.crm_project.modules.kpi_management.application.dto.response.KpiTargetResponse;
import org.example.crm_project.modules.kpi_management.domain.entity.KpiAssignment;
import org.example.crm_project.modules.kpi_management.domain.entity.KpiConfig;
import org.example.crm_project.modules.kpi_management.domain.entity.KpiTarget;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class KpiConfigMapper {


    public static KpiConfig toKpiConfig(KpiConfigRequest req) {
        if (req == null) return null;
        KpiConfig domain = new KpiConfig();
        domain.setName(req.getName());
        domain.setStartDate(req.getStartDate());
        domain.setEndDate(req.getEndDate());
        domain.setStatus(req.getStatus());
        domain.setDescription(req.getDescription());
        domain.setCreatedBy(req.getCreatedBy());
        
        domain.setTargets(toKpiTargets(req.getTargets()));
        domain.setAssignments(toKpiAssignments(req.getAssignments()));
        
        return domain;
    }

    public static List<KpiTarget> toKpiTargets(List<KpiTargetRequest> requests) {
        if (requests == null) return Collections.emptyList();
        return requests.stream()
                .map(KpiConfigMapper::toKpiTarget)
                .collect(Collectors.toList());
    }

    private static KpiTarget toKpiTarget(KpiTargetRequest req) {
        if (req == null) return null;
        KpiTarget domain = new KpiTarget();
        domain.setMetricType(req.getMetricType());
        domain.setTargetValue(req.getTargetValue());
        return domain;
    }

    public static List<KpiAssignment> toKpiAssignments(List<KpiAssignmentRequest> requests) {
        if (requests == null) return Collections.emptyList();
        return requests.stream()
                .map(KpiConfigMapper::toKpiAssignment)
                .collect(Collectors.toList());
    }

    private static KpiAssignment toKpiAssignment(KpiAssignmentRequest req) {
        if (req == null) return null;
        KpiAssignment domain = new KpiAssignment();
        domain.setUserId(req.getUserId());
        domain.setOrganizationId(req.getOrganizationId());
        domain.setCommissionPercent(req.getCommissionPercent());
        return domain;
    }

    /**
     * Chuyển đổi từ đối tượng KpiConfig sang KpiConfigResponse để trả về Frontend
     */
    public static KpiConfigResponse toKpiConfigResponse(KpiConfig domain) {
        if (domain == null) return null;
        return KpiConfigResponse.builder()
                .id(domain.getId())
                .name(domain.getName())
                .startDate(domain.getStartDate())
                .endDate(domain.getEndDate())
                .status(domain.getStatus())
                .description(domain.getDescription())
                .createdAt(domain.getCreatedAt())
                .targets(toKpiTargetResponses(domain.getTargets()))
                .assignments(toKpiAssignmentResponses(domain.getAssignments()))
                .build();
    }

    private static List<KpiTargetResponse> toKpiTargetResponses(List<KpiTarget> domains) {
        if (domains == null) return Collections.emptyList();
        return domains.stream()
                .map(d -> KpiTargetResponse.builder()
                        .id(d.getId())
                        .metricType(d.getMetricType())
                        .targetValue(d.getTargetValue())
                        .build())
                .collect(Collectors.toList());
    }

    private static List<KpiAssignmentResponse> toKpiAssignmentResponses(List<KpiAssignment> domains) {
        if (domains == null)
            return Collections.emptyList();
        return domains.stream()
                .map(d -> KpiAssignmentResponse.builder()
                        .id(d.getId())
                        .userId(d.getUserId())
                        .organizationId(d.getOrganizationId())
                        .commissionPercent(d.getCommissionPercent())
                        .build())
                .collect(Collectors.toList());
    }
}
