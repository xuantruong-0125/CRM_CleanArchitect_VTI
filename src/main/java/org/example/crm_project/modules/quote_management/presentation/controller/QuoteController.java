package org.example.crm_project.modules.quote_management.presentation.controller;

import lombok.RequiredArgsConstructor;
import org.example.crm_project.modules.quote_management.application.dto.request.CreateQuoteRequest;
import org.example.crm_project.modules.quote_management.application.dto.request.UpdateQuoteRequest;
import org.example.crm_project.modules.quote_management.application.dto.response.QuoteDetailResponse;
import org.example.crm_project.modules.quote_management.application.dto.response.QuoteResponse;
import org.example.crm_project.modules.quote_management.application.service.QuoteService;
import org.example.crm_project.modules.customers.domain.entity.Customer;
import org.example.crm_project.modules.quote_management.domain.entity.DocumentTemplate;
import org.example.crm_project.modules.quote_management.application.dto.response.QuoteFormProductResponse;
import org.example.crm_project.shared.model.PageResult;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * REST Controller cho module Báo Giá.
 * Chỉ chứa logic HTTP, mọi business logic delegated to QuoteService.
 */
@RestController
@RequestMapping("/api/quotes")
@RequiredArgsConstructor
public class QuoteController {

    private final QuoteService quoteService;

    // ===== GET ALL (có tìm kiếm và phân trang) =====
    @GetMapping
    public ResponseEntity<PageResult<QuoteResponse>> getAll(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Long customerId,
            @RequestParam(required = false) Integer statusId,
            @RequestParam(required = false) String quoteNumber,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fromDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime toDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id,desc") String sort) {
        String[] parts = sort.split(",", 2);
        String sortField = parts[0];
        String sortDir = parts.length > 1 ? parts[1] : "desc";
        return ResponseEntity.ok(quoteService.search(keyword, customerId, statusId, quoteNumber, fromDate, toDate, page, size, sortField, sortDir));
    }

    // ===== GET BY CUSTOMER (phân trang tối ưu bằng index) =====
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<PageResult<QuoteResponse>> getByCustomer(
            @PathVariable Long customerId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(quoteService.getByCustomer(customerId, page, size));
    }

    // ===== GET BY ID =====
    @GetMapping("/{id}")
    public ResponseEntity<QuoteDetailResponse> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(quoteService.getById(id));
    }

    // ===== CREATE =====
    @PostMapping
    public ResponseEntity<QuoteDetailResponse> create(@RequestBody CreateQuoteRequest req) {
        return ResponseEntity.ok(quoteService.create(req));
    }

    // ===== UPDATE =====
    @PutMapping("/{id}")
    public ResponseEntity<QuoteDetailResponse> update(
            @PathVariable Integer id,
            @RequestBody UpdateQuoteRequest req) {
        return ResponseEntity.ok(quoteService.update(id, req));
    }

    // ===== UPDATE STATUS =====
    @PatchMapping("/{id}/status")
    public ResponseEntity<Void> updateStatus(
            @PathVariable Integer id,
            @RequestBody Map<String, Integer> body) {
        quoteService.updateStatus(id, body.get("statusId"));
        return ResponseEntity.ok().build();
    }

    // ===== SOFT DELETE =====
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> delete(@PathVariable Integer id) {
        quoteService.softDelete(id);
        Map<String, String> res = new HashMap<>();
        res.put("message", "Xóa báo giá thành công");
        return ResponseEntity.ok(res);
    }

    // ===== CHECK QUOTE NUMBER (AJAX) =====
    @GetMapping("/check-number")
    public ResponseEntity<Map<String, Boolean>> checkNumber(
            @RequestParam String number,
            @RequestParam(required = false) Integer excludeId) {
        boolean exists = quoteService.isQuoteNumberExists(number, excludeId);
        Map<String, Boolean> res = new HashMap<>();
        res.put("exists", exists);
        return ResponseEntity.ok(res);
    }

    // ===== GET FORM DATA (dropdown cho UI) =====

    @GetMapping("/form-data/customers")
    public ResponseEntity<List<Customer>> getCustomers() {
        return ResponseEntity.ok(quoteService.getAllActiveCustomers());
    }

    @GetMapping("/form-data/customers/search")
    public ResponseEntity<List<Customer>> searchCustomers(@RequestParam(value = "keyword", defaultValue = "") String keyword) {
        return ResponseEntity.ok(quoteService.searchCustomers(keyword));
    }

    @GetMapping("/form-data/products")
    public ResponseEntity<List<QuoteFormProductResponse>> getProducts() {
        return ResponseEntity.ok(quoteService.getAllActiveProducts());
    }

    @GetMapping("/form-data/templates")
    public ResponseEntity<List<DocumentTemplate>> getTemplates() {
        return ResponseEntity.ok(quoteService.getQuoteTemplates());
    }
}
