package org.example.crm_project.modules.opportunity_management.presentation.controller;

import org.example.crm_project.modules.opportunity_management.application.dto.OpportunityRequest;
import org.example.crm_project.modules.opportunity_management.application.dto.OpportunityResponse;
import org.example.crm_project.modules.opportunity_management.application.dto.PipelineStageResponse;
import org.example.crm_project.modules.opportunity_management.application.service.*;
import org.example.crm_project.shared.model.PageResult;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * REST Controller – Opportunity.
 *
 * Base URL: /api/opportunities
 *
 * Endpoints:
 *   GET    /api/opportunities              – Lấy danh sách có filter + phân trang
 *   GET    /api/opportunities/{id}         – Lấy chi tiết
 *   POST   /api/opportunities              – Tạo mới
 *   PUT    /api/opportunities/{id}         – Cập nhật
 *   DELETE /api/opportunities/{id}         – Xóa
 *   GET    /api/opportunities/names        – Lấy danh sách tên (autocomplete)
 *   GET    /api/opportunities/stages?pipelineId=X  – Stage theo pipeline (cascading)
 */
@RestController
@RequestMapping("/api/opportunities")
@RequiredArgsConstructor
public class OpportunityRestController {

    private final OpportunityService opportunityService;
    private final PipelineStageService stageService;

    // ── GET /api/opportunities ──────────────────────────────────────────────
    @GetMapping
    public ResponseEntity<PageResult<OpportunityResponse>> getAll(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Integer customerId,
            @RequestParam(required = false) Integer assignedUserId,
            @RequestParam(required = false) Integer pipelineId,
            @RequestParam(required = false) Integer stageId,
            @RequestParam(required = false) String healthStatus,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFrom,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateTo,
            @RequestParam(required = false, defaultValue = "") String sortField,
            @RequestParam(required = false, defaultValue = "asc") String sortDir,
            @RequestParam(required = false, defaultValue = "1") int page,
            @RequestParam(required = false, defaultValue = "20") int size) {

        Page<OpportunityResponse> pageData = opportunityService.filter(
                keyword, customerId, assignedUserId,
                pipelineId, stageId, healthStatus,
                dateFrom, dateTo, sortField, sortDir, page, size);

        PageResult<OpportunityResponse> result = PageResult.<OpportunityResponse>builder()
                .data(pageData.getContent())
                .currentPage(page)
                .pageSize(size)
                .totalElements(pageData.getTotalElements())
                .totalPages(pageData.getTotalPages())
                .hasNext(pageData.hasNext())
                .hasPrevious(pageData.hasPrevious())
                .build();

        return ResponseEntity.ok(result);
    }

    // ── GET /api/opportunities/{id} ─────────────────────────────────────────
    @GetMapping("/{id}")
    public ResponseEntity<OpportunityResponse> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(opportunityService.getById(id));
    }

    // ── POST /api/opportunities ─────────────────────────────────────────────
    @PostMapping
    public ResponseEntity<OpportunityResponse> create(@RequestBody OpportunityRequest request) {
        OpportunityResponse created = opportunityService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // ── PUT /api/opportunities/{id} ─────────────────────────────────────────
    @PutMapping("/{id}")
    public ResponseEntity<OpportunityResponse> update(
            @PathVariable Integer id,
            @RequestBody OpportunityRequest request) {
        return ResponseEntity.ok(opportunityService.update(id, request));
    }

    // ── DELETE /api/opportunities/{id} ──────────────────────────────────────
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        opportunityService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // ── GET /api/opportunities/names ────────────────────────────────────────
    @GetMapping("/names")
    public ResponseEntity<List<String>> getAllNames() {
        return ResponseEntity.ok(opportunityService.getAllNames());
    }

    // ── GET /api/opportunities/stages?pipelineId=X ──────────────────────────
    @GetMapping("/stages")
    public ResponseEntity<List<PipelineStageResponse>> getStagesByPipeline(
            @RequestParam Integer pipelineId) {
        return ResponseEntity.ok(stageService.getByPipelineId(pipelineId));
    }
}
