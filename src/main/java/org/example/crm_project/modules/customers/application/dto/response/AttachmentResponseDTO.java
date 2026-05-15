package org.example.crm_project.modules.customers.application.dto.response;

import java.time.LocalDateTime;

/**
 * DTO: AttachmentResponseDTO
 * Response DTO for Attachment entity
 */
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

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getFileName() { return fileName; }
    public void setFileName(String fileName) { this.fileName = fileName; }

    public String getFileType() { return fileType; }
    public void setFileType(String fileType) { this.fileType = fileType; }

    public Integer getFileSize() { return fileSize; }
    public void setFileSize(Integer fileSize) { this.fileSize = fileSize; }

    public String getFilePath() { return filePath; }
    public void setFilePath(String filePath) { this.filePath = filePath; }

    public String getRelatedToType() { return relatedToType; }
    public void setRelatedToType(String relatedToType) { this.relatedToType = relatedToType; }

    public Long getRelatedToId() { return relatedToId; }
    public void setRelatedToId(Long relatedToId) { this.relatedToId = relatedToId; }

    public Long getUploadedBy() { return uploadedBy; }
    public void setUploadedBy(Long uploadedBy) { this.uploadedBy = uploadedBy; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
