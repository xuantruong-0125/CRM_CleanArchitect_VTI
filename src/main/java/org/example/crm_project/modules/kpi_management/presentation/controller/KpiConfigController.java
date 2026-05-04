package org.example.crm_project.modules.kpi_management.presentation.controller;

import lombok.RequiredArgsConstructor;
import org.example.crm_project.modules.kpi_management.application.dto.request.KpiConfigRequest;
import org.example.crm_project.modules.kpi_management.application.dto.request.UpdateKpiConfigRequest;
import org.example.crm_project.modules.kpi_management.application.dto.response.KpiConfigResponse;
import org.example.crm_project.modules.kpi_management.application.dto.response.PaginatedResponse;
import org.example.crm_project.modules.kpi_management.application.interfaces.KpiConfigService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/kpi-configs")
@RequiredArgsConstructor
public class KpiConfigController {
    private final KpiConfigService kpiConfigService;

    @GetMapping
    public ResponseEntity<PaginatedResponse<KpiConfigResponse>> findAll(
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(kpiConfigService.findAll(keyword, page, size));
    }

    @PostMapping
    public ResponseEntity<KpiConfigResponse> create(@RequestBody KpiConfigRequest request) {
        return ResponseEntity.ok(kpiConfigService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<KpiConfigResponse> update(@PathVariable Integer id, @RequestBody UpdateKpiConfigRequest request) {
        return ResponseEntity.ok(kpiConfigService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        kpiConfigService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
