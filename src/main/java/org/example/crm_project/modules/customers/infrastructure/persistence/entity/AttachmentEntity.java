package org.example.crm_project.modules.customers.infrastructure.persistence.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Persistence Entity: AttachmentEntity
 * Represents a file attachment
 */
@Entity
@Table(name = "attachments", indexes = {
    @Index(name = "idx_attachable", columnList = "attachable_type, attachable_id"),
    @Index(name = "fk_attach_user", columnList = "uploaded_by")
})
public class AttachmentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "file_name", nullable = false, length = 255)
    private String fileName;
    
    @Column(name = "file_type", length = 50)
    private String fileType;
    
    @Column(name = "file_size")
    private Integer fileSize;
    
    @Column(name = "file_url", length = 255, nullable = false)
    private String filePath;
    
    @Column(name = "attachable_type", length = 255, nullable = false)
    private String relatedToType;
    
    @Column(name = "attachable_id", nullable = false)
    private Long relatedToId;
    
    @Column(name = "uploaded_by", nullable = false)
    private Long uploadedBy;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Transient
    private LocalDateTime updatedAt;
    
    @Transient
    private LocalDateTime deletedAt;
    
    @PrePersist
    protected void onCreate() {
        if (createdAt == null) createdAt = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
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
    
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
    
    public LocalDateTime getDeletedAt() { return deletedAt; }
    public void setDeletedAt(LocalDateTime deletedAt) { this.deletedAt = deletedAt; }
}
