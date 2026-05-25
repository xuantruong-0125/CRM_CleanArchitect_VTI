package org.example.crm_project.modules.customers_managerment.domain.entity;

import java.time.LocalDateTime;

/**
 * Domain Entity: Attachment
 * Đại diện cho tài liệu đính kèm
 */
public class Attachment {
    private Long id;
    private String fileName;
    private String fileType;
    private Integer fileSize;
    private String filePath;
    private String relatedToType;  // CUSTOMER, CONTACT, OPPORTUNITY, CONTRACT, etc.
    private Long relatedToId;
    private Long uploadedBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;

    // Constructor
    public Attachment() {
    }

    public Attachment(String fileName, String fileType, Integer fileSize, String filePath) {
        this.fileName = fileName;
        this.fileType = fileType;
        this.fileSize = fileSize;
        this.filePath = filePath;
        this.createdAt = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public Integer getFileSize() {
        return fileSize;
    }

    public void setFileSize(Integer fileSize) {
        this.fileSize = fileSize;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getRelatedToType() {
        return relatedToType;
    }

    public void setRelatedToType(String relatedToType) {
        this.relatedToType = relatedToType;
    }

    public Long getRelatedToId() {
        return relatedToId;
    }

    public void setRelatedToId(Long relatedToId) {
        this.relatedToId = relatedToId;
    }

    public Long getUploadedBy() {
        return uploadedBy;
    }

    public void setUploadedBy(Long uploadedBy) {
        this.uploadedBy = uploadedBy;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }
}
