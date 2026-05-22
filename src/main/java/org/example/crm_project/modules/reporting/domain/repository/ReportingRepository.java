package org.example.crm_project.modules.reporting.domain.repository;

import org.example.crm_project.modules.reporting.domain.model.DashboardData;
import org.example.crm_project.modules.reporting.domain.model.UserKpiRow;

import java.util.List;

public interface ReportingRepository {
    List<UserKpiRow> getDetailReport(Long kpiConfigId);
    DashboardData getDashboardData(Long kpiConfigId, Long userId);
}
