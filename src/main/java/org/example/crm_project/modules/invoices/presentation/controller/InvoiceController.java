package org.example.crm_project.modules.invoices.presentation.controller;

import org.example.crm_project.modules.invoices.application.dto.request.BulkActionRequest;
import org.example.crm_project.modules.invoices.application.dto.request.CreateInvoiceRequest;
import org.example.crm_project.modules.invoices.application.dto.response.InvoiceResponse;
import org.example.crm_project.modules.invoices.application.service.InvoiceService;
import org.example.crm_project.modules.invoices.domain.constant.InvoiceStatus;
import lombok.RequiredArgsConstructor;
import org.example.crm_project.modules.products_managerment.presentation.dto.ApiResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.example.crm_project.modules.invoices.infrastructure.persistence.repository.InvoiceProductViewRepository;
import org.example.crm_project.modules.invoices.infrastructure.persistence.entity.InvoiceProductViewEntity;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/invoices")
@RequiredArgsConstructor
public class InvoiceController {
    private final InvoiceService invoiceService;
    private final InvoiceProductViewRepository invoiceProductViewRepository;
    @GetMapping
    public ResponseEntity<Page<InvoiceResponse>> getInvoices(
            @RequestParam(required = false) String invoiceNumber,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate issueDate,
            @RequestParam(required = false) InvoiceStatus status,
            @RequestParam(required = false) Long assignedTo,
            Pageable pageable) {
        return ResponseEntity.ok(invoiceService.searchInvoices(invoiceNumber, issueDate, status, assignedTo, pageable));
    }
    @GetMapping("/lookup/products")
    public ApiResponse<List<InvoiceProductViewEntity>> getProductsLookup() {
        return ApiResponse.success(invoiceProductViewRepository.findAll());
    }
    @GetMapping("/{id}")
    public ResponseEntity<InvoiceResponse> getInvoice(@PathVariable Long id) {
        return ResponseEntity.ok(invoiceService.getInvoiceById(id));
    }

    @PostMapping
    public ResponseEntity<InvoiceResponse> createInvoice(@RequestBody CreateInvoiceRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(invoiceService.createInvoice(request));
    }

    @PostMapping("/from-order/{orderId}")
    public ResponseEntity<InvoiceResponse> createInvoiceFromOrder(@PathVariable Long orderId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(invoiceService.generateInvoiceFromOrder(orderId));
    }

    @PostMapping("/bulk-assign")
    public ResponseEntity<Void> bulkAssign(@RequestBody BulkActionRequest request) {
        invoiceService.bulkAssign(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/bulk-delete")
    public ResponseEntity<Void> bulkDelete(@RequestBody BulkActionRequest request) {
        invoiceService.bulkDelete(request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/export-pdf")
    public ResponseEntity<byte[]> exportPdf(@PathVariable Long id) {
        byte[] pdfBytes = invoiceService.exportPdf(id);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "invoice_" + id + ".pdf");

        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }
}