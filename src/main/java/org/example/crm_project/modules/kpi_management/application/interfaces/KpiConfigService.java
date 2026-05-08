package org.example.crm_project.modules.kpi_management.application.interfaces;

import org.example.crm_project.modules.kpi_management.application.dto.request.KpiConfigRequest;
import org.example.crm_project.modules.kpi_management.application.dto.request.UpdateKpiConfigRequest;
import org.example.crm_project.modules.kpi_management.application.dto.response.KpiConfigResponse;
import org.example.crm_project.modules.kpi_management.application.dto.response.PaginatedResponse;

public interface KpiConfigService {
    PaginatedResponse<KpiConfigResponse> findAll(String keyword, int page, int size);
    KpiConfigResponse create(KpiConfigRequest request);
    KpiConfigResponse update(Integer id, UpdateKpiConfigRequest request);
    void delete(Integer id);
}
