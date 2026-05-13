package org.example.crm_project.modules.customers.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "customers")
public class CustomerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "customer_code", length = 50, unique = true)
    private String customerCode;

    @Column(name = "name", length = 255, nullable = false)
    private String name;

    @Column(name = "short_name", length = 255)
    private String shortName;

    @Column(name = "type")
    private String type; // Lưu 'B2B' hoặc 'B2C'

    @Column(name = "phone", length = 255)
    private String phone;

    @Column(name = "email", length = 255)
    private String email;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;
    
    // Lưu ý: Mối quan hệ với ContactEntity sẽ được định nghĩa ở phía ContactEntity 
    // để tránh module shared phụ thuộc ngược lại vào module contacts.
}
