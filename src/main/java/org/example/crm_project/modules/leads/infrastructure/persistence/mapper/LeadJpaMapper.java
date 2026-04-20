package org.example.crm_project.modules.leads.infrastructure.persistence.mapper;

import org.example.crm_project.modules.leads.domain.entity.Lead;
import org.example.crm_project.modules.leads.infrastructure.persistence.entity.LeadEntity;

import java.util.ArrayList;
import java.util.List;

public class LeadJpaMapper {

    public static LeadEntity toEntity(Lead lead) {
        LeadEntity entity = new LeadEntity();
        entity.setId(lead.getId());
        entity.setContactName(lead.getContactName());
        entity.setCompanyName(lead.getCompanyName());
        entity.setPhone(lead.getPhone());
        entity.setEmail(lead.getEmail());
        entity.setAddress(lead.getAddress());
        entity.setWebsite(lead.getWebsite());
        entity.setTaxCode(lead.getTaxCode());
        entity.setCitizenId(lead.getCitizenId());
        entity.setProvinceId(lead.getProvinceId());
        entity.setDescription(lead.getDescription());
        entity.setExpectedRevenue(lead.getExpectedRevenue());
        entity.setSourceId(lead.getSourceId());
        entity.setCampaignId(lead.getCampaignId());
        entity.setOrganizationId(lead.getOrganizationId());
        entity.setAssignedTo(lead.getAssignedTo());
        entity.setIsConverted(lead.getIsConverted());
        entity.setConvertedCustomerId(lead.getConvertedCustomerId());
        entity.setConvertedContactId(lead.getConvertedContactId());
        entity.setConvertedOpportunityId(lead.getConvertedOpportunityId());
        entity.setConvertedAt(lead.getConvertedAt());
        entity.setCreatedBy(lead.getCreatedBy());
        entity.setUpdatedBy(lead.getUpdatedBy());
        entity.setCreatedAt(lead.getCreatedAt());
        entity.setUpdatedAt(lead.getUpdatedAt());
        entity.setDeletedAt(lead.getDeletedAt());
        entity.setStatusId(lead.getStatusId());
        return entity;
    }

    public static Lead toDomain(LeadEntity entity, List<Long> productInterestIds) {
        return Lead.builder()
                .id(entity.getId())
                .contactName(entity.getContactName())
                .companyName(entity.getCompanyName())
                .phone(entity.getPhone())
                .email(entity.getEmail())
                .address(entity.getAddress())
                .website(entity.getWebsite())
                .taxCode(entity.getTaxCode())
                .citizenId(entity.getCitizenId())
                .provinceId(entity.getProvinceId())
                .description(entity.getDescription())
                .expectedRevenue(entity.getExpectedRevenue())
                .sourceId(entity.getSourceId())
                .campaignId(entity.getCampaignId())
                .organizationId(entity.getOrganizationId())
                .assignedTo(entity.getAssignedTo())
                .isConverted(entity.getIsConverted())
                .convertedCustomerId(entity.getConvertedCustomerId())
                .convertedContactId(entity.getConvertedContactId())
                .convertedOpportunityId(entity.getConvertedOpportunityId())
                .convertedAt(entity.getConvertedAt())
                .createdBy(entity.getCreatedBy())
                .updatedBy(entity.getUpdatedBy())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .deletedAt(entity.getDeletedAt())
                .statusId(entity.getStatusId())
                .productInterestIds(productInterestIds == null ? new ArrayList<>() : productInterestIds)
                .build();
    }

    public static Lead toDomain(LeadEntity entity) {
        return toDomain(entity, new ArrayList<>());
    }
}