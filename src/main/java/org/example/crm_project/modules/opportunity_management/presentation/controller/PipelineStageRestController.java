package org.example.crm_project.modules.opportunity_management.presentation.controller;

import org.example.crm_project.modules.opportunity_management.application.dto.PipelineStageRequest;
import org.example.crm_project.modules.opportunity_management.application.dto.PipelineStageResponse;
import org.example.crm_project.modules.opportunity_management.application.service.PipelineStageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;

/**
 * REST Controller – PipelineStage.
 *
 * Base URL: /api/stages
 *
 * Endpoints:
 *   GET    /api/stages                        – Lấy tất cả (có thể lọc theo pipelineId)
 *   GET    /api/stages/{id}                   – Lấy chi tiết
 *   POST   /api/stages                        – Tạo mới
 *   PUT    /api/stages/{id}                   – Cập nhật
 *   DELETE /api/stages/{id}                   – Xóa
 */
@RestController
@RequestMapping("/api/stages")
@RequiredArgsConstructor
public class PipelineStageRestController {

    private final PipelineStageService stageService;

    // ── GET /api/stages  (có thể kèm ?pipelineId=X) ────────────────────────
    @GetMapping
    public ResponseEntity<List<PipelineStageResponse>> getAll(
            @RequestParam(required = false) Integer pipelineId) {
        List<PipelineStageResponse> list;
        if (pipelineId != null) {
            list = stageService.getByPipelineId(pipelineId);
        } else {
            list = stageService.getAll();
            list.sort(Comparator.comparing(
                    s -> s.getPipelineName() != null ? s.getPipelineName() : ""));
        }
        return ResponseEntity.ok(list);
    }

    // ── GET /api/stages/{id} ────────────────────────────────────────────────
    @GetMapping("/{id}")
    public ResponseEntity<PipelineStageResponse> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(stageService.getById(id));
    }

    // ── POST /api/stages ────────────────────────────────────────────────────
    @PostMapping
    public ResponseEntity<PipelineStageResponse> create(@RequestBody PipelineStageRequest request) {
        PipelineStageResponse created = stageService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // ── PUT /api/stages/{id} ────────────────────────────────────────────────
    @PutMapping("/{id}")
    public ResponseEntity<PipelineStageResponse> update(
            @PathVariable Integer id,
            @RequestBody PipelineStageRequest request) {
        return ResponseEntity.ok(stageService.update(id, request));
    }

    // ── DELETE /api/stages/{id} ─────────────────────────────────────────────
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        stageService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
