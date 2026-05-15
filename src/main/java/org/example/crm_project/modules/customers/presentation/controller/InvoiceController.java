package org.example.crm_project.modules.customers.presentation.controller;

import org.example.crm_project.modules.customers.application.dto.request.CreateInvoiceDTO;
import org.example.crm_project.modules.customers.application.dto.response.InvoiceResponseDTO;
import org.example.crm_project.modules.customers.application.service.InvoiceService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.Optional;

/**
 * Controller: InvoiceController
 * REST endpoints for Invoice management
 */
@RestController
@RequestMapping("/api/invoices")
@Validated
public class InvoiceController {

    private final InvoiceService invoiceService;

    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @PostMapping
    public ResponseEntity<InvoiceResponseDTO> createInvoice(@Valid @RequestBody CreateInvoiceDTO createDTO) {
        InvoiceResponseDTO created = invoiceService.createInvoice(createDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<InvoiceResponseDTO> getInvoiceById(@PathVariable Long id) {
        InvoiceResponseDTO invoice = invoiceService.getInvoiceById(id);
        return ResponseEntity.ok(invoice);
    }

    @GetMapping("/code/{invoiceCode}")
    public ResponseEntity<?> getInvoiceByCode(@PathVariable String invoiceCode) {
        Optional<InvoiceResponseDTO> invoice = invoiceService.getInvoiceByCode(invoiceCode);
        return invoice.isPresent() ? ResponseEntity.ok(invoice.get()) : ResponseEntity.notFound().build();
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<Page<InvoiceResponseDTO>> getInvoicesByCustomer(
            @PathVariable Long customerId,
            Pageable pageable) {
        Page<InvoiceResponseDTO> invoices = invoiceService.getInvoicesByCustomer(customerId, pageable);
        return ResponseEntity.ok(invoices);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<Page<InvoiceResponseDTO>> getInvoicesByStatus(
            @PathVariable String status,
            Pageable pageable) {
        Page<InvoiceResponseDTO> invoices = invoiceService.getInvoicesByStatus(status, pageable);
        return ResponseEntity.ok(invoices);
    }

    @PutMapping("/{id}")
    public ResponseEntity<InvoiceResponseDTO> updateInvoice(
            @PathVariable Long id,
            @Valid @RequestBody CreateInvoiceDTO createDTO) {
        InvoiceResponseDTO updated = invoiceService.updateInvoice(id, createDTO);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInvoice(@PathVariable Long id) {
        invoiceService.deleteInvoice(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/count")
    public ResponseEntity<Long> countInvoices() {
        long count = invoiceService.countInvoices();
        return ResponseEntity.ok(count);
    }
}
