package org.example.crm_project.modules.opportunity_management.presentation.controller;

import org.example.crm_project.modules.opportunity_management.application.dto.LossReasonRequest;
import org.example.crm_project.modules.opportunity_management.application.dto.LossReasonResponse;
import org.example.crm_project.modules.opportunity_management.application.service.LossReasonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller – LossReason.
 *
 * Base URL: /api/loss-reasons
 *
 * Endpoints:
 *   GET    /api/loss-reasons       – Lấy tất cả lý do thua
 *   GET    /api/loss-reasons/{id}  – Lấy chi tiết
 *   POST   /api/loss-reasons       – Tạo mới
 *   PUT    /api/loss-reasons/{id}  – Cập nhật
 *   DELETE /api/loss-reasons/{id}  – Xóa
 */
@RestController
@RequestMapping("/api/loss-reasons")
@RequiredArgsConstructor
public class LossReasonRestController {

    private final LossReasonService lossReasonService;

    // ── GET /api/loss-reasons ───────────────────────────────────────────────
    @GetMapping
    public ResponseEntity<List<LossReasonResponse>> getAll() {
        return ResponseEntity.ok(lossReasonService.getAll());
    }

    // ── GET /api/loss-reasons/{id} ──────────────────────────────────────────
    @GetMapping("/{id}")
    public ResponseEntity<LossReasonResponse> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(lossReasonService.getById(id));
    }

    // ── POST /api/loss-reasons ──────────────────────────────────────────────
    @PostMapping
    public ResponseEntity<LossReasonResponse> create(@RequestBody LossReasonRequest request) {
        // default isActive = true nếu không truyền
        if (request.getIsActive() == null) request.setIsActive(true);
        LossReasonResponse created = lossReasonService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // ── PUT /api/loss-reasons/{id} ──────────────────────────────────────────
    @PutMapping("/{id}")
    public ResponseEntity<LossReasonResponse> update(
            @PathVariable Integer id,
            @RequestBody LossReasonRequest request) {
        if (request.getIsActive() == null) request.setIsActive(false);
        return ResponseEntity.ok(lossReasonService.update(id, request));
    }

    // ── DELETE /api/loss-reasons/{id} ───────────────────────────────────────
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        lossReasonService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
