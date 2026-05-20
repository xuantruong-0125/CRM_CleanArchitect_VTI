package org.example.crm_project.modules.quote_management.domain.entity;

import org.example.crm_project.modules.quote_management.domain.constant.TemplateType;

import java.time.LocalDateTime;

/**
 * Domain entity cho mẫu tài liệu.
 * POJO thuần túy - không có JPA annotation.
 */
public class DocumentTemplate {

    private Integer id;
    private TemplateType type;
    private String name;
    private String contentHtml;
    private Boolean isActive;
    private LocalDateTime createdAt;

    // ===== Constructor load từ DB =====
    public DocumentTemplate(Integer id, TemplateType type, String name,
                            String contentHtml, Boolean isActive, LocalDateTime createdAt) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.contentHtml = contentHtml;
        this.isActive = isActive;
        this.createdAt = createdAt;
    }

    // ===== Getter =====
    public Integer getId() { return id; }
    public TemplateType getType() { return type; }
    public String getName() { return name; }
    public String getContentHtml() { return contentHtml; }
    public Boolean getIsActive() { return isActive; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}
