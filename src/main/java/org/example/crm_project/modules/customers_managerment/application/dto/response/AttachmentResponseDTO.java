package org.example.crm_project.modules.customers_managerment.application.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * DTO: AttachmentResponseDTO
 * Response DTO for Attachment entity
 */
@Data
public class AttachmentResponseDTO {
    private Long id;
    private String fileName;
    private String fileType;
    private Integer fileSize;
    private String filePath;
    private String relatedToType;
    private Long relatedToId;
    private Long uploadedBy;
    private LocalDateTime createdAt;

    public AttachmentResponseDTO() {}

    public AttachmentResponseDTO(Long id, String fileName, String fileType) {
        this.id = id;
        this.fileName = fileName;
        this.fileType = fileType;
    }
}
