package org.example.crm_project.modules.leads.application.mapper;

import org.example.crm_project.modules.leads.application.dto.request.CreateLeadRequest;
import org.example.crm_project.modules.leads.application.dto.response.LeadResponse;
import org.example.crm_project.modules.leads.domain.entity.Lead;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LeadMapper {

    public static Lead toDomain(CreateLeadRequest request) {
        return Lead.builder()
                .contactName(trim(request.getContactName()))
                .companyName(trim(request.getCompanyName()))
                .phone(trim(request.getPhone()))
                .email(trim(request.getEmail()))
                .address(trim(request.getAddress()))
                .website(trim(request.getWebsite()))
                .taxCode(trim(request.getTaxCode()))
                .citizenId(trim(request.getCitizenId()))
                .provinceId(request.getProvinceId())
                .description(trim(request.getDescription()))
                .expectedRevenue(request.getExpectedRevenue())
                .sourceId(request.getSourceId())
                .campaignId(request.getCampaignId())
                .organizationId(request.getOrganizationId())
                .assignedTo(request.getAssignedTo())
                .statusId(request.getStatusId())
                .productInterestIds(normalizeIds(request.getProductInterestIds()))
                .build();
    }

    public static LeadResponse toResponse(Lead lead) {
        return LeadResponse.builder()
                .id(lead.getId())
                .contactName(lead.getContactName())
                .companyName(lead.getCompanyName())
                .phone(lead.getPhone())
                .email(lead.getEmail())
                .address(lead.getAddress())
                .website(lead.getWebsite())
                .taxCode(lead.getTaxCode())
                .citizenId(lead.getCitizenId())
                .provinceId(lead.getProvinceId())
                .description(lead.getDescription())
                .expectedRevenue(lead.getExpectedRevenue())
                .sourceId(lead.getSourceId())
                .campaignId(lead.getCampaignId())
                .organizationId(lead.getOrganizationId())
                .assignedTo(lead.getAssignedTo())
                .isConverted(lead.getIsConverted())
                .convertedCustomerId(lead.getConvertedCustomerId())
                .convertedContactId(lead.getConvertedContactId())
                .convertedOpportunityId(lead.getConvertedOpportunityId())
                .convertedAt(lead.getConvertedAt())
                .createdBy(lead.getCreatedBy())
                .updatedBy(lead.getUpdatedBy())
                .createdAt(lead.getCreatedAt())
                .updatedAt(lead.getUpdatedAt())
                .deletedAt(lead.getDeletedAt())
                .statusId(lead.getStatusId())
                .productInterestIds(normalizeIds(lead.getProductInterestIds()))
                .build();
    }

    public static Lead copy(Lead source) {
        return Lead.builder()
                .id(source.getId())
                .contactName(source.getContactName())
                .companyName(source.getCompanyName())
                .phone(source.getPhone())
                .email(source.getEmail())
                .address(source.getAddress())
                .website(source.getWebsite())
                .taxCode(source.getTaxCode())
                .citizenId(source.getCitizenId())
                .provinceId(source.getProvinceId())
                .description(source.getDescription())
                .expectedRevenue(source.getExpectedRevenue())
                .sourceId(source.getSourceId())
                .campaignId(source.getCampaignId())
                .organizationId(source.getOrganizationId())
                .assignedTo(source.getAssignedTo())
                .isConverted(source.getIsConverted())
                .convertedCustomerId(source.getConvertedCustomerId())
                .convertedContactId(source.getConvertedContactId())
                .convertedOpportunityId(source.getConvertedOpportunityId())
                .convertedAt(source.getConvertedAt())
                .createdBy(source.getCreatedBy())
                .updatedBy(source.getUpdatedBy())
                .createdAt(source.getCreatedAt())
                .updatedAt(source.getUpdatedAt())
                .deletedAt(source.getDeletedAt())
                .statusId(source.getStatusId())
                .productInterestIds(normalizeIds(source.getProductInterestIds()))
                .build();
    }

    private static List<Long> normalizeIds(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return new ArrayList<>();
        }

        return ids.stream()
                .filter(Objects::nonNull)
                .distinct()
                .toList();
    }

    private static String trim(String value) {
        if (value == null) {
            return null;
        }

        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }
}