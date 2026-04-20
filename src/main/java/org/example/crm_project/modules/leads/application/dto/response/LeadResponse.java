package org.example.crm_project.modules.leads.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LeadResponse {

    private Long id;
    private String contactName;
    private String companyName;
    private String phone;
    private String email;
    private String address;
    private String website;
    private String taxCode;
    private String citizenId;
    private Integer provinceId;
    private String description;
    private BigDecimal expectedRevenue;
    private Long sourceId;
    private Long campaignId;
    private Long organizationId;
    private Long assignedTo;
    private Boolean isConverted;
    private Long convertedCustomerId;
    private Long convertedContactId;
    private Long convertedOpportunityId;
    private LocalDateTime convertedAt;
    private Long createdBy;
    private Long updatedBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
    private Long statusId;

    @Builder.Default
    private List<Long> productInterestIds = new ArrayList<>();
}