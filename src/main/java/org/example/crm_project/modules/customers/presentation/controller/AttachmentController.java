package org.example.crm_project.modules.customers.presentation.controller;

import org.example.crm_project.modules.customers.application.dto.request.CreateAttachmentDTO;
import org.example.crm_project.modules.customers.application.dto.response.AttachmentResponseDTO;
import org.example.crm_project.modules.customers.application.service.AttachmentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

/**
 * Controller: AttachmentController
 * REST endpoints for Attachment management
 */
@RestController
@RequestMapping("/api/attachments")
@Validated
public class AttachmentController {

    private final AttachmentService attachmentService;

    public AttachmentController(AttachmentService attachmentService) {
        this.attachmentService = attachmentService;
    }

    @PostMapping
    public ResponseEntity<AttachmentResponseDTO> createAttachment(@Valid @RequestBody CreateAttachmentDTO createDTO) {
        AttachmentResponseDTO created = attachmentService.createAttachment(createDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AttachmentResponseDTO> getAttachmentById(@PathVariable Long id) {
        AttachmentResponseDTO attachment = attachmentService.getAttachmentById(id);
        return ResponseEntity.ok(attachment);
    }

    @GetMapping("/related/{relatedToType}/{relatedToId}")
    public ResponseEntity<List<AttachmentResponseDTO>> getAttachmentsByRelatedTo(
            @PathVariable String relatedToType,
            @PathVariable Long relatedToId) {
        List<AttachmentResponseDTO> attachments = attachmentService.getAttachmentsByRelatedTo(relatedToType, relatedToId);
        return ResponseEntity.ok(attachments);
    }

    @GetMapping("/related-paginated/{relatedToType}/{relatedToId}")
    public ResponseEntity<Page<AttachmentResponseDTO>> getAttachmentsByRelatedToPaginated(
            @PathVariable String relatedToType,
            @PathVariable Long relatedToId,
            Pageable pageable) {
        Page<AttachmentResponseDTO> attachments = attachmentService.getAttachmentsByRelatedToPaginated(relatedToType, relatedToId, pageable);
        return ResponseEntity.ok(attachments);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AttachmentResponseDTO> updateAttachment(
            @PathVariable Long id,
            @Valid @RequestBody CreateAttachmentDTO createDTO) {
        AttachmentResponseDTO updated = attachmentService.updateAttachment(id, createDTO);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAttachment(@PathVariable Long id) {
        attachmentService.deleteAttachment(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/related/{relatedToType}/{relatedToId}")
    public ResponseEntity<Void> deleteAttachmentsByRelatedTo(
            @PathVariable String relatedToType,
            @PathVariable Long relatedToId) {
        attachmentService.deleteAttachmentsByRelatedTo(relatedToType, relatedToId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/count")
    public ResponseEntity<Long> countAttachments() {
        long count = attachmentService.countAttachments();
        return ResponseEntity.ok(count);
    }
}
