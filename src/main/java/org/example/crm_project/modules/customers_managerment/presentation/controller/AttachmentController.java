package org.example.crm_project.modules.customers_managerment.presentation.controller;

import org.example.crm_project.modules.customers_managerment.application.dto.request.CreateAttachmentDTO;
import org.example.crm_project.modules.customers_managerment.application.dto.response.AttachmentResponseDTO;
import org.example.crm_project.modules.customers_managerment.application.dto.response.UploadAttachmentResponseDTO;
import org.example.crm_project.modules.customers_managerment.application.service.AttachmentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAnyAuthority('CUSTOMER_CREATE', 'CUSTOMER_UPDATE')")
    public ResponseEntity<UploadAttachmentResponseDTO> uploadFile(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("File tải lên không được để trống");
        }

        try {
            String originalFilename = file.getOriginalFilename();
            String fileName = originalFilename != null ? originalFilename : "unknown";
            String extension = "";
            int dotIndex = fileName.lastIndexOf(".");
            if (dotIndex > 0) {
                extension = fileName.substring(dotIndex);
                fileName = fileName.substring(0, dotIndex);
            }

            String timeStamp = LocalDateTime.now()
                    .format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));

            String newFileName = timeStamp + "_" + fileName + extension;
            Path path = Paths.get("uploads/" + newFileName);

            Files.createDirectories(path.getParent());
            Files.write(path, file.getBytes());

            String fileUrl = "/uploads/" + newFileName;
            String fileType = extension.startsWith(".") ? extension.substring(1) : extension;

            UploadAttachmentResponseDTO response = UploadAttachmentResponseDTO.builder()
                    .fileName(originalFilename)
                    .fileType(fileType)
                    .fileSize(file.getSize())
                    .filePath(fileUrl)
                    .build();

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IOException ex) {
            throw new RuntimeException("Lỗi khi lưu file: " + ex.getMessage());
        }
    }

    @PostMapping
    @PreAuthorize("hasAuthority('CUSTOMER_CREATE')")
    public ResponseEntity<AttachmentResponseDTO> createAttachment(@Valid @RequestBody CreateAttachmentDTO createDTO) {
        AttachmentResponseDTO created = attachmentService.createAttachment(createDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('CUSTOMER_VIEW')")
    public ResponseEntity<AttachmentResponseDTO> getAttachmentById(@PathVariable Long id) {
        AttachmentResponseDTO attachment = attachmentService.getAttachmentById(id);
        return ResponseEntity.ok(attachment);
    }

    @GetMapping("/related/{relatedToType}/{relatedToId}")
    @PreAuthorize("hasAuthority('CUSTOMER_VIEW')")
    public ResponseEntity<List<AttachmentResponseDTO>> getAttachmentsByRelatedTo(
            @PathVariable String relatedToType,
            @PathVariable Long relatedToId) {
        List<AttachmentResponseDTO> attachments = attachmentService.getAttachmentsByRelatedTo(relatedToType, relatedToId);
        return ResponseEntity.ok(attachments);
    }

    @GetMapping("/related-paginated/{relatedToType}/{relatedToId}")
    @PreAuthorize("hasAuthority('CUSTOMER_VIEW')")
    public ResponseEntity<Page<AttachmentResponseDTO>> getAttachmentsByRelatedToPaginated(
            @PathVariable String relatedToType,
            @PathVariable Long relatedToId,
            Pageable pageable) {
        Page<AttachmentResponseDTO> attachments = attachmentService.getAttachmentsByRelatedToPaginated(relatedToType, relatedToId, pageable);
        return ResponseEntity.ok(attachments);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('CUSTOMER_UPDATE')")
    public ResponseEntity<AttachmentResponseDTO> updateAttachment(
            @PathVariable Long id,
            @Valid @RequestBody CreateAttachmentDTO createDTO) {
        AttachmentResponseDTO updated = attachmentService.updateAttachment(id, createDTO);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('CUSTOMER_DELETE')")
    public ResponseEntity<Void> deleteAttachment(@PathVariable Long id) {
        attachmentService.deleteAttachment(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/related/{relatedToType}/{relatedToId}")
    @PreAuthorize("hasAuthority('CUSTOMER_DELETE')")
    public ResponseEntity<Void> deleteAttachmentsByRelatedTo(
            @PathVariable String relatedToType,
            @PathVariable Long relatedToId) {
        attachmentService.deleteAttachmentsByRelatedTo(relatedToType, relatedToId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/count")
    @PreAuthorize("hasAuthority('CUSTOMER_VIEW')")
    public ResponseEntity<Long> countAttachments() {
        long count = attachmentService.countAttachments();
        return ResponseEntity.ok(count);
    }
}
