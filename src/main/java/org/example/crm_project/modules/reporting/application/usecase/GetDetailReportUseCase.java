package org.example.crm_project.modules.reporting.application.usecase;

import org.example.crm_project.modules.reporting.domain.model.UserKpiRow;
import org.example.crm_project.modules.reporting.domain.repository.ReportingRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetDetailReportUseCase {
    private final ReportingRepository reportingRepository;

    public GetDetailReportUseCase(ReportingRepository reportingRepository) {
        this.reportingRepository = reportingRepository;
    }

    public List<UserKpiRow> execute(Long kpiConfigId) {
        return reportingRepository.getDetailReport(kpiConfigId);
    }
}
