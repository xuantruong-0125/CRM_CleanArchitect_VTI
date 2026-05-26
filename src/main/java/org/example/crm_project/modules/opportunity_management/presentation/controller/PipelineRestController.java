package org.example.crm_project.modules.opportunity_management.presentation.controller;

import org.example.crm_project.modules.opportunity_management.application.dto.PipelineRequest;
import org.example.crm_project.modules.opportunity_management.application.dto.PipelineResponse;
import org.example.crm_project.modules.opportunity_management.application.service.PipelineService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller – Pipeline.
 *
 * Base URL: /api/pipelines
 *
 * Endpoints:
 *   GET    /api/pipelines       – Lấy tất cả pipeline
 *   GET    /api/pipelines/{id}  – Lấy chi tiết
 *   POST   /api/pipelines       – Tạo mới
 *   PUT    /api/pipelines/{id}  – Cập nhật
 *   DELETE /api/pipelines/{id}  – Xóa
 */
@RestController
@RequestMapping("/api/pipelines")
@RequiredArgsConstructor
public class PipelineRestController {

    private final PipelineService pipelineService;

    // ── GET /api/pipelines ──────────────────────────────────────────────────
    @GetMapping
    public ResponseEntity<List<PipelineResponse>> getAll() {
        return ResponseEntity.ok(pipelineService.getAll());
    }

    // ── GET /api/pipelines/{id} ─────────────────────────────────────────────
    @GetMapping("/{id}")
    public ResponseEntity<PipelineResponse> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(pipelineService.getById(id));
    }

    // ── POST /api/pipelines ─────────────────────────────────────────────────
    @PostMapping
    public ResponseEntity<PipelineResponse> create(@RequestBody PipelineRequest request) {
        PipelineResponse created = pipelineService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // ── PUT /api/pipelines/{id} ─────────────────────────────────────────────
    @PutMapping("/{id}")
    public ResponseEntity<PipelineResponse> update(
            @PathVariable Integer id,
            @RequestBody PipelineRequest request) {
        return ResponseEntity.ok(pipelineService.update(id, request));
    }

    // ── DELETE /api/pipelines/{id} ──────────────────────────────────────────
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        pipelineService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
