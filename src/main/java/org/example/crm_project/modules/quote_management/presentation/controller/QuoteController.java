package org.example.crm_project.modules.quote_management.presentation.controller;

import lombok.RequiredArgsConstructor;
import org.example.crm_project.modules.quote_management.application.dto.request.CreateQuoteRequest;
import org.example.crm_project.modules.quote_management.application.dto.request.UpdateQuoteRequest;
import org.example.crm_project.modules.quote_management.application.dto.response.QuoteDetailResponse;
import org.example.crm_project.modules.quote_management.application.dto.response.QuoteResponse;
import org.example.crm_project.modules.quote_management.application.service.QuoteService;
import org.example.crm_project.modules.quote_management.domain.entity.Customer;
import org.example.crm_project.modules.quote_management.domain.entity.DocumentTemplate;
import org.example.crm_project.modules.quote_management.domain.entity.Product;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    // ===== GET ALL (có tìm kiếm) =====
    @GetMapping
    public ResponseEntity<List<QuoteResponse>> getAll(
            @RequestParam(required = false) String keyword) {
        return ResponseEntity.ok(quoteService.getAll(keyword));
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

    @GetMapping("/form-data/products")
    public ResponseEntity<List<Product>> getProducts() {
        return ResponseEntity.ok(quoteService.getAllActiveProducts());
    }

    @GetMapping("/form-data/templates")
    public ResponseEntity<List<DocumentTemplate>> getTemplates() {
        return ResponseEntity.ok(quoteService.getQuoteTemplates());
    }
}
