package org.example.crm_project.modules.opportunity_management.application.mapper;

import org.example.crm_project.modules.opportunity_management.application.dto.OpportunityRequest;
import org.example.crm_project.modules.opportunity_management.application.dto.OpportunityResponse;
import org.example.crm_project.modules.opportunity_management.domain.entity.Opportunity;
import org.springframework.stereotype.Component;

/**
 * Mapper chuyển đổi giữa Domain Model ↔ DTO.
 * Tách biệt hoàn toàn Domain khỏi lớp Presentation.
 */
@Component
public class OpportunityMapper {

    /**
     * Chuyển OpportunityRequest (form data) → Domain Model.
     */
    public Opportunity toDomain(OpportunityRequest request) {
        return Opportunity.builder()
                .id(request.getId())
                .name(request.getName())
                .customerId(request.getCustomerId())
                .assignedUserId(request.getAssignedUserId())
                .pipelineId(request.getPipelineId())
                .stageId(request.getStageId())
                .lossReasonId(request.getLossReasonId())
                .totalAmount(request.getTotalAmount())
                .depositAmount(request.getDepositAmount())
                .currencyCode(request.getCurrencyCode())
                .exchangeRate(request.getExchangeRate())
                .healthStatus(request.getHealthStatus())
                .expectedCloseDate(request.getExpectedCloseDate())
                .build();
    }

    /**
     * Chuyển Domain Model → OpportunityResponse (view data).
     */
    public OpportunityResponse toResponse(Opportunity opportunity) {
        return OpportunityResponse.builder()
                .id(opportunity.getId())
                .name(opportunity.getName())
                .customerId(opportunity.getCustomerId())
                .customerName(opportunity.getCustomerName())
                .assignedUserId(opportunity.getAssignedUserId())
                .assignedUserFullName(opportunity.getAssignedUserFullName())
                .pipelineId(opportunity.getPipelineId())
                .pipelineName(opportunity.getPipelineName())
                .stageId(opportunity.getStageId())
                .stageName(opportunity.getStageName())
                .lossReasonId(opportunity.getLossReasonId())
                .lossReasonName(opportunity.getLossReasonName())
                .totalAmount(opportunity.getTotalAmount())
                .depositAmount(opportunity.getDepositAmount())
                .remainingAmount(opportunity.getRemainingAmount())
                .currencyCode(opportunity.getCurrencyCode())
                .exchangeRate(opportunity.getExchangeRate())
                .healthStatus(opportunity.getHealthStatus())
                .expectedCloseDate(opportunity.getExpectedCloseDate())
                .build();
    }
}
