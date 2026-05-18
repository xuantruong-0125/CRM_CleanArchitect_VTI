package org.example.crm_project.modules.kpi_management.application.usecase;

import lombok.RequiredArgsConstructor;
import org.example.crm_project.modules.kpi_management.domain.entity.KpiConfig;
import org.example.crm_project.modules.kpi_management.domain.repository.KpiConfigRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetMyKpiConfigsUseCase {
    private final KpiConfigRepository kpiConfigRepository;

    public List<KpiConfig> execute(Integer userId, Integer organizationId) {
        return kpiConfigRepository.findAssignedConfigs(userId, organizationId);
    }
}
