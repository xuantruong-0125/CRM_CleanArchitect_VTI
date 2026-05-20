package org.example.crm_project.modules.reporting.presentation.rest;

import org.example.crm_project.modules.reporting.application.usecase.GetDashboardDataUseCase;
import org.example.crm_project.modules.reporting.application.usecase.GetDetailReportUseCase;
import org.example.crm_project.modules.reporting.domain.model.DashboardData;
import org.example.crm_project.modules.reporting.domain.model.UserKpiRow;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reports")
public class ReportingController {

    private final GetDetailReportUseCase getDetailReportUseCase;
    private final GetDashboardDataUseCase getDashboardDataUseCase;

    public ReportingController(GetDetailReportUseCase getDetailReportUseCase,
            GetDashboardDataUseCase getDashboardDataUseCase) {
        this.getDetailReportUseCase = getDetailReportUseCase;
        this.getDashboardDataUseCase = getDashboardDataUseCase;
    }

    @GetMapping("/detail/{kpiConfigId}")
    public ResponseEntity<List<UserKpiRow>> getDetailReport(@PathVariable Long kpiConfigId) {
        return ResponseEntity.ok(getDetailReportUseCase.execute(kpiConfigId));
    }

    @GetMapping("/dashboard/{kpiConfigId}")
    public ResponseEntity<DashboardData> getDashboardData(
            @PathVariable Long kpiConfigId,
            @RequestParam(required = false) Long userId) {
        return ResponseEntity.ok(getDashboardDataUseCase.execute(kpiConfigId, userId));
    }
}
