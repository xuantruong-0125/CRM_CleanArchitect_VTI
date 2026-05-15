package org.example.crm_project.modules.customers.application.mapper;

import org.example.crm_project.modules.customers.application.dto.request.CreateOpportunityDTO;
import org.example.crm_project.modules.customers.application.dto.response.OpportunityResponseDTO;
import org.example.crm_project.modules.customers.domain.entity.Opportunity;
import org.springframework.stereotype.Component;

/**
 * Mapper: OpportunityMapper
 * DTO ↔ Domain entity conversion
 */
@Component
public class OpportunityMapper {

    public Opportunity toEntity(CreateOpportunityDTO createDTO) {
        if (createDTO == null) return null;

        Opportunity opportunity = new Opportunity();
        opportunity.setName(createDTO.getName());
        opportunity.setCustomerId(createDTO.getCustomerId());
        opportunity.setPipelineId(createDTO.getPipelineId());
        opportunity.setStageId(createDTO.getStageId());
        opportunity.setTotalAmount(createDTO.getTotalAmount());
        opportunity.setDepositAmount(createDTO.getDepositAmount());
        opportunity.setRemainingAmount(createDTO.getRemainingAmount());
        opportunity.setCurrencyCode(createDTO.getCurrencyCode());
        opportunity.setExchangeRate(createDTO.getExchangeRate());
        opportunity.setExpectedCloseDate(createDTO.getExpectedCloseDate());
        opportunity.setLossReasonId(createDTO.getLossReasonId());
        opportunity.setHealthStatus(createDTO.getHealthStatus());
        opportunity.setAssignedUserId(createDTO.getAssignedUserId());

        return opportunity;
    }

    public OpportunityResponseDTO toResponseDTO(Opportunity opportunity) {
        if (opportunity == null) return null;

        OpportunityResponseDTO dto = new OpportunityResponseDTO();
        dto.setId(opportunity.getId());
        dto.setName(opportunity.getName());
        dto.setCustomerId(opportunity.getCustomerId());
        dto.setPipelineId(opportunity.getPipelineId());
        dto.setStageId(opportunity.getStageId());
        dto.setTotalAmount(opportunity.getTotalAmount());
        dto.setDepositAmount(opportunity.getDepositAmount());
        dto.setRemainingAmount(opportunity.getRemainingAmount());
        dto.setCurrencyCode(opportunity.getCurrencyCode());
        dto.setExchangeRate(opportunity.getExchangeRate());
        dto.setExpectedCloseDate(opportunity.getExpectedCloseDate());
        dto.setLossReasonId(opportunity.getLossReasonId());
        dto.setHealthStatus(opportunity.getHealthStatus());
        dto.setAssignedUserId(opportunity.getAssignedUserId());
        dto.setCreatedAt(opportunity.getCreatedAt());
        dto.setUpdatedAt(opportunity.getUpdatedAt());

        return dto;
    }

    public void updateEntityFromDTO(Opportunity opportunity, CreateOpportunityDTO createDTO) {
        if (opportunity == null || createDTO == null) return;

        opportunity.setName(createDTO.getName());
        opportunity.setCustomerId(createDTO.getCustomerId());
        opportunity.setPipelineId(createDTO.getPipelineId());
        opportunity.setStageId(createDTO.getStageId());
        opportunity.setTotalAmount(createDTO.getTotalAmount());
        opportunity.setDepositAmount(createDTO.getDepositAmount());
        opportunity.setRemainingAmount(createDTO.getRemainingAmount());
        opportunity.setCurrencyCode(createDTO.getCurrencyCode());
        opportunity.setExchangeRate(createDTO.getExchangeRate());
        opportunity.setExpectedCloseDate(createDTO.getExpectedCloseDate());
        opportunity.setLossReasonId(createDTO.getLossReasonId());
        opportunity.setHealthStatus(createDTO.getHealthStatus());
        opportunity.setAssignedUserId(createDTO.getAssignedUserId());
    }
}
