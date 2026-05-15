package org.example.crm_project.modules.customers.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * DTO: CreateAttachmentDTO
 * Create/Update DTO for Attachment
 */
public class CreateAttachmentDTO {
    @NotBlank(message = "Tên file không được để trống")
    private String fileName;

    private String fileType;
    private Integer fileSize;
    private String filePath;
    
    @NotNull(message = "Loại liên quan không được để trống")
    private String relatedToType;
    
    @NotNull(message = "ID liên quan không được để trống")
    private Long relatedToId;

    private Long uploadedBy;

    public CreateAttachmentDTO() {}

    // Getters & Setters
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
}
