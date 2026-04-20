package org.example.crm_project.modules.quote_management.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * JPA Entity cho bảng customers (read-only reference trong module báo giá).
 */
@Entity
@Table(name = "customers")
@Getter
@Setter
public class CustomerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "customer_code", length = 50)
    private String customerCode;

    @Column(name = "name", length = 255, nullable = false)
    private String name;

    @Column(name = "short_name", length = 255)
    private String shortName;

    @Column(name = "type")
    private String type; // lưu dạng String trong DB

    @Column(name = "phone", length = 255)
    private String phone;

    @Column(name = "email", length = 255)
    private String email;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;
}
