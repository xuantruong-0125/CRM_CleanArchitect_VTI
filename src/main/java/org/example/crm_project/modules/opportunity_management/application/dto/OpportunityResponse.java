package org.example.crm_project.modules.opportunity_management.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * DTO trả về danh sách / chi tiết Opportunity cho lớp Presentation.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OpportunityResponse {
    private Integer id;
    private String name;

    private Integer customerId;
    private String customerName;

    private Integer assignedUserId;
    private String assignedUserFullName;

    private Integer pipelineId;
    private String pipelineName;

    private Integer stageId;
    private String stageName;

    private Integer lossReasonId;
    private String lossReasonName;

    private BigDecimal totalAmount;
    private BigDecimal depositAmount;
    private BigDecimal remainingAmount;
    private String currencyCode;
    private BigDecimal exchangeRate;
    private String healthStatus;
    private LocalDate expectedCloseDate;
}
