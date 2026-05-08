package org.example.crm_project.modules.reporting.application.usecase;

import org.example.crm_project.modules.reporting.domain.model.DashboardData;
import org.example.crm_project.modules.reporting.domain.repository.ReportingRepository;
import org.springframework.stereotype.Service;

@Service
public class GetDashboardDataUseCase {
    private final ReportingRepository reportingRepository;

    public GetDashboardDataUseCase(ReportingRepository reportingRepository) {
        this.reportingRepository = reportingRepository;
    }

    public DashboardData execute(Long kpiConfigId, Long userId) {
        return reportingRepository.getDashboardData(kpiConfigId, userId);
    }
}
