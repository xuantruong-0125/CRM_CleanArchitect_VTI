package org.example.crm_project.modules.customers_managerment.presentation.controller;

import org.example.crm_project.modules.customers_managerment.application.dto.request.CreateFeedbackDTO;
import org.example.crm_project.modules.customers_managerment.application.dto.response.FeedbackResponseDTO;
import org.example.crm_project.modules.customers_managerment.application.service.FeedbackService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

/**
 * Controller: FeedbackController
 * REST endpoints for Feedback management
 */
@RestController
@RequestMapping("/api/feedbacks")
@Validated
public class FeedbackController {

    private final FeedbackService feedbackService;

    public FeedbackController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('CUSTOMER_CREATE')")
    public ResponseEntity<FeedbackResponseDTO> createFeedback(@Valid @RequestBody CreateFeedbackDTO createDTO) {
        FeedbackResponseDTO created = feedbackService.createFeedback(createDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('CUSTOMER_VIEW')")
    public ResponseEntity<FeedbackResponseDTO> getFeedbackById(@PathVariable Long id) {
        FeedbackResponseDTO feedback = feedbackService.getFeedbackById(id);
        return ResponseEntity.ok(feedback);
    }

    @GetMapping("/customer/{customerId}")
    @PreAuthorize("hasAuthority('CUSTOMER_VIEW')")
    public ResponseEntity<Page<FeedbackResponseDTO>> getFeedbacksByCustomer(
            @PathVariable Long customerId,
            Pageable pageable) {
        Page<FeedbackResponseDTO> feedbacks = feedbackService.getFeedbacksByCustomer(customerId, pageable);
        return ResponseEntity.ok(feedbacks);
    }

    @GetMapping("/status/{status}")
    @PreAuthorize("hasAuthority('CUSTOMER_VIEW')")
    public ResponseEntity<Page<FeedbackResponseDTO>> getFeedbacksByStatus(
            @PathVariable String status,
            Pageable pageable) {
        Page<FeedbackResponseDTO> feedbacks = feedbackService.getFeedbacksByStatus(status, pageable);
        return ResponseEntity.ok(feedbacks);
    }

    @GetMapping("/priority/{priority}")
    @PreAuthorize("hasAuthority('CUSTOMER_VIEW')")
    public ResponseEntity<Page<FeedbackResponseDTO>> getFeedbacksByPriority(
            @PathVariable String priority,
            Pageable pageable) {
        Page<FeedbackResponseDTO> feedbacks = feedbackService.getFeedbacksByPriority(priority, pageable);
        return ResponseEntity.ok(feedbacks);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('CUSTOMER_UPDATE')")
    public ResponseEntity<FeedbackResponseDTO> updateFeedback(
            @PathVariable Long id,
            @Valid @RequestBody CreateFeedbackDTO createDTO) {
        FeedbackResponseDTO updated = feedbackService.updateFeedback(id, createDTO);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('CUSTOMER_DELETE')")
    public ResponseEntity<Void> deleteFeedback(@PathVariable Long id) {
        feedbackService.deleteFeedback(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/count")
    @PreAuthorize("hasAuthority('CUSTOMER_VIEW')")
    public ResponseEntity<Long> countFeedbacks() {
        long count = feedbackService.countFeedbacks();
        return ResponseEntity.ok(count);
    }
}
