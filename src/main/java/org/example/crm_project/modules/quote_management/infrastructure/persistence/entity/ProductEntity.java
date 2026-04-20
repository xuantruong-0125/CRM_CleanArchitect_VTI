package org.example.crm_project.modules.quote_management.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * JPA Entity cho bảng products (read-only reference trong module báo giá).
 */
@Entity
@Table(name = "products")
@Getter
@Setter
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "sku_code", length = 100)
    private String skuCode;

    @Column(name = "name", length = 255, nullable = false)
    private String name;

    @Column(name = "type")
    private String type; // lưu dạng String trong DB

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;
}
