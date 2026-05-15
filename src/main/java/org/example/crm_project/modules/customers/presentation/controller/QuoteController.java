package org.example.crm_project.modules.customers.presentation.controller;

import org.example.crm_project.modules.customers.application.dto.request.CreateQuoteDTO;
import org.example.crm_project.modules.customers.application.dto.response.QuoteResponseDTO;
import org.example.crm_project.modules.customers.application.service.QuoteService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.Optional;

/**
 * Controller: QuoteController
 * REST endpoints for Quote management
 */
@RestController
@RequestMapping("/api/quotes")
@Validated
public class QuoteController {

    private final QuoteService quoteService;

    public QuoteController(QuoteService quoteService) {
        this.quoteService = quoteService;
    }

    @PostMapping
    public ResponseEntity<QuoteResponseDTO> createQuote(@Valid @RequestBody CreateQuoteDTO createDTO) {
        QuoteResponseDTO created = quoteService.createQuote(createDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuoteResponseDTO> getQuoteById(@PathVariable Long id) {
        QuoteResponseDTO quote = quoteService.getQuoteById(id);
        return ResponseEntity.ok(quote);
    }

    @GetMapping("/code/{quoteCode}")
    public ResponseEntity<?> getQuoteByCode(@PathVariable String quoteCode) {
        Optional<QuoteResponseDTO> quote = quoteService.getQuoteByCode(quoteCode);
        return quote.isPresent() ? ResponseEntity.ok(quote.get()) : ResponseEntity.notFound().build();
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<Page<QuoteResponseDTO>> getQuotesByCustomer(
            @PathVariable Long customerId,
            Pageable pageable) {
        Page<QuoteResponseDTO> quotes = quoteService.getQuotesByCustomer(customerId, pageable);
        return ResponseEntity.ok(quotes);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<Page<QuoteResponseDTO>> getQuotesByStatus(
            @PathVariable String status,
            Pageable pageable) {
        Page<QuoteResponseDTO> quotes = quoteService.getQuotesByStatus(status, pageable);
        return ResponseEntity.ok(quotes);
    }

    @PutMapping("/{id}")
    public ResponseEntity<QuoteResponseDTO> updateQuote(
            @PathVariable Long id,
            @Valid @RequestBody CreateQuoteDTO createDTO) {
        QuoteResponseDTO updated = quoteService.updateQuote(id, createDTO);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuote(@PathVariable Long id) {
        quoteService.deleteQuote(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/count")
    public ResponseEntity<Long> countQuotes() {
        long count = quoteService.countQuotes();
        return ResponseEntity.ok(count);
    }
}
