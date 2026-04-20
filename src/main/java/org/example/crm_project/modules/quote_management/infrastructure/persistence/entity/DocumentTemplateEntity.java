package org.example.crm_project.modules.quote_management.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * JPA Entity cho bảng document_templates (read-only reference trong module báo giá).
 */
@Entity
@Table(name = "document_templates")
@Getter
@Setter
public class DocumentTemplateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "type", nullable = false)
    private String type; // lưu dạng String trong DB

    @Column(name = "name", length = 255, nullable = false)
    private String name;

    @Column(name = "content_html", columnDefinition = "LONGTEXT")
    private String contentHtml;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt;
}
