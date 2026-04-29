package org.example.crm_project.modules.contacts_managerment.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.crm_project.modules.contacts_managerment.domain.entity.CustomerType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "customers")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CustomerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "parent_id")
    private Long parentId;

    @Column(name = "customer_code", length = 50)
    private String customerCode;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private CustomerType type;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "short_name")
    private String shortName;

    @Column(name = "tax_code", length = 50)
    private String taxCode;

    @Column(name = "phone")
    private String phone;

    @Column(name = "email")
    private String email;

    @Column(name = "fax", length = 50)
    private String fax;

    @Column(name = "established_date")
    private LocalDate establishedDate;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "source_id")
    private Long sourceId;

    @Column(name = "status_id")
    private Long statusId;

    @Column(name = "tier_id")
    private Long tierId;

    @Column(name = "assigned_to")
    private Long assignedTo;

    @Column(name = "created_by")
    private Long createdBy;

    @Column(name = "updated_by")
    private Long updatedBy;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<ContactEntity> contacts;
}
