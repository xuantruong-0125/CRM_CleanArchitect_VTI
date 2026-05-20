package org.example.crm_project.modules.opportunity_management.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * DTO nhận dữ liệu từ form tạo mới / cập nhật Opportunity.
 * Tách biệt hoàn toàn khỏi Domain Entity.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OpportunityRequest {
    private Integer id;
    private String name;

    private Integer customerId;
    private Integer assignedUserId;

    private Integer pipelineId;
    private Integer stageId;
    private Integer lossReasonId;

    private BigDecimal totalAmount;
    private BigDecimal depositAmount;
    private String currencyCode;
    private BigDecimal exchangeRate;
    private String healthStatus;
    private LocalDate expectedCloseDate;
}
