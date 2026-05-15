package org.example.crm_project.modules.customers.presentation.controller;

import org.example.crm_project.modules.customers.application.dto.request.CreateOpportunityDTO;
import org.example.crm_project.modules.customers.application.dto.response.OpportunityResponseDTO;
import org.example.crm_project.modules.customers.application.service.OpportunityService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

/**
 * Controller: OpportunityController
 * REST endpoints for Opportunity management
 */
@RestController
@RequestMapping("/api/opportunities")
@Validated
public class OpportunityController {

    private final OpportunityService opportunityService;

    public OpportunityController(OpportunityService opportunityService) {
        this.opportunityService = opportunityService;
    }

    @PostMapping
    public ResponseEntity<OpportunityResponseDTO> createOpportunity(@Valid @RequestBody CreateOpportunityDTO createDTO) {
        OpportunityResponseDTO created = opportunityService.createOpportunity(createDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OpportunityResponseDTO> getOpportunityById(@PathVariable Long id) {
        OpportunityResponseDTO opportunity = opportunityService.getOpportunityById(id);
        return ResponseEntity.ok(opportunity);
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<Page<OpportunityResponseDTO>> getOpportunitiesByCustomer(
            @PathVariable Long customerId,
            Pageable pageable) {
        Page<OpportunityResponseDTO> opportunities = opportunityService.getOpportunitiesByCustomer(customerId, pageable);
        return ResponseEntity.ok(opportunities);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<Page<OpportunityResponseDTO>> getOpportunitiesByAssignedUser(
            @PathVariable Long userId,
            Pageable pageable) {
        Page<OpportunityResponseDTO> opportunities = opportunityService.getOpportunitiesByAssignedUser(userId, pageable);
        return ResponseEntity.ok(opportunities);
    }

    @GetMapping("/status/{healthStatus}")
    public ResponseEntity<Page<OpportunityResponseDTO>> getOpportunitiesByHealthStatus(
            @PathVariable String healthStatus,
            Pageable pageable) {
        Page<OpportunityResponseDTO> opportunities = opportunityService.getOpportunitiesByHealthStatus(healthStatus, pageable);
        return ResponseEntity.ok(opportunities);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OpportunityResponseDTO> updateOpportunity(
            @PathVariable Long id,
            @Valid @RequestBody CreateOpportunityDTO createDTO) {
        OpportunityResponseDTO updated = opportunityService.updateOpportunity(id, createDTO);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOpportunity(@PathVariable Long id) {
        opportunityService.deleteOpportunity(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/count")
    public ResponseEntity<Long> countOpportunities() {
        long count = opportunityService.countOpportunities();
        return ResponseEntity.ok(count);
    }
}
