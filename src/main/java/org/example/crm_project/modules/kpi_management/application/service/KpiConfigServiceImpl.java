package org.example.crm_project.modules.kpi_management.application.service;

import lombok.RequiredArgsConstructor;
import org.example.crm_project.modules.kpi_management.application.dto.request.KpiConfigRequest;
import org.example.crm_project.modules.kpi_management.application.dto.request.UpdateKpiConfigRequest;
import org.example.crm_project.modules.kpi_management.application.dto.response.KpiConfigResponse;
import org.example.crm_project.modules.kpi_management.application.dto.response.PaginatedResponse;
import org.example.crm_project.modules.kpi_management.application.interfaces.KpiConfigService;
import org.example.crm_project.modules.kpi_management.application.mapper.KpiConfigMapper;
import org.example.crm_project.modules.kpi_management.domain.entity.KpiConfig;
import org.example.crm_project.modules.kpi_management.domain.repository.KpiConfigRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class KpiConfigServiceImpl implements KpiConfigService {

    private final KpiConfigRepository kpiConfigRepository;

    @Override
    public PaginatedResponse<KpiConfigResponse> findAll(String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        Page<KpiConfig> configPage;
        
        if (keyword != null && !keyword.trim().isEmpty()) {
            configPage = kpiConfigRepository.search(keyword, pageable);
        } else {
            configPage = kpiConfigRepository.findAll(pageable);
        }

        List<KpiConfigResponse> responses = configPage.getContent().stream()
                .map(KpiConfigMapper::toKpiConfigResponse)
                .collect(Collectors.toList());

        return PaginatedResponse.<KpiConfigResponse>builder()
                .content(responses)
                .pageNumber(configPage.getNumber())
                .pageSize(configPage.getSize())
                .totalElements(configPage.getTotalElements())
                .totalPages(configPage.getTotalPages())
                .last(configPage.isLast())
                .build();
    }

    @Override
    @Transactional
    public KpiConfigResponse create(KpiConfigRequest request) {
        KpiConfig domain = KpiConfigMapper.toKpiConfig(request);
        KpiConfig saved = kpiConfigRepository.save(domain);
        return KpiConfigMapper.toKpiConfigResponse(saved);
    }

    @Override
    @Transactional
    public KpiConfigResponse update(Integer id, UpdateKpiConfigRequest request) {
        KpiConfig domain = kpiConfigRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("KPI Configuration not found: " + id));

        domain.update(
                request.getName(),
                request.getStartDate(),
                request.getEndDate(),
                request.getStatus(),
                request.getDescription(),
                request.getUpdatedBy(),
                KpiConfigMapper.toKpiTargets(request.getTargets()),
                KpiConfigMapper.toKpiAssignments(request.getAssignments())
        );

        KpiConfig saved = kpiConfigRepository.save(domain);
        return KpiConfigMapper.toKpiConfigResponse(saved);
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        KpiConfig domain = kpiConfigRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("KPI Configuration not found: " + id));
        domain.softDelete();
        kpiConfigRepository.delete(domain);
    }
}
