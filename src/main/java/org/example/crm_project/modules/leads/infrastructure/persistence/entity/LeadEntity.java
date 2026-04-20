package org.example.crm_project.modules.leads.infrastructure.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "leads")
@Getter
@Setter
public class LeadEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "contact_name", nullable = false)
    private String contactName;

    @Column(name = "company_name")
    private String companyName;

    private String phone;

    private String email;

    private String address;

    private String website;

    @Column(name = "tax_code")
    private String taxCode;

    @Column(name = "citizen_id")
    private String citizenId;

    @Column(name = "province_id")
    private Integer provinceId;

    private String description;

    @Column(name = "expected_revenue")
    private BigDecimal expectedRevenue;

    @Column(name = "source_id")
    private Long sourceId;

    @Column(name = "campaign_id")
    private Long campaignId;

    @Column(name = "organization_id")
    private Long organizationId;

    @Column(name = "assigned_to")
    private Long assignedTo;

    @Column(name = "is_converted")
    private Boolean isConverted;

    @Column(name = "converted_customer_id")
    private Long convertedCustomerId;

    @Column(name = "converted_contact_id")
    private Long convertedContactId;

    @Column(name = "converted_opportunity_id")
    private Long convertedOpportunityId;

    @Column(name = "converted_at")
    private LocalDateTime convertedAt;

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

    @Column(name = "status_id", nullable = false)
    private Long statusId;
}