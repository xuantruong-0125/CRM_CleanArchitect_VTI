package org.example.crm_project.modules.opportunity_management.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Domain Entity – Aggregate Root của module Opportunity.
 * Chứa toàn bộ business logic và không phụ thuộc bất kỳ framework nào.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Opportunity {

    private Integer id;
    private String name;

    // ID tham chiếu ngoài module (Customer, User)
    private Integer customerId;
    private Integer assignedUserId;

    // Tham chiếu nội bộ (Pipeline, Stage, LossReason)
    private Integer pipelineId;
    private Integer stageId;
    private Integer lossReasonId;

    // Thông tin tài chính
    private BigDecimal totalAmount;
    private BigDecimal depositAmount;
    private BigDecimal remainingAmount;
    private String currencyCode;
    private BigDecimal exchangeRate;

    // Trạng thái
    private String healthStatus;
    private LocalDate expectedCloseDate;

    // Snapshot cho display (không lưu DB trực tiếp, lấy từ join)
    private String customerName;
    private String assignedUserFullName;
    private String pipelineName;
    private String stageName;
    private String lossReasonName;

    // =========== Business Logic ===========

    /**
     * Tính lại số tiền còn lại dựa trên tổng tiền và tiền đặt cọc.
     */
    public void recalculateRemainingAmount() {
        if (this.totalAmount != null && this.depositAmount != null) {
            this.remainingAmount = this.totalAmount.subtract(this.depositAmount);
        } else if (this.totalAmount != null) {
            this.remainingAmount = this.totalAmount;
        } else {
            this.remainingAmount = BigDecimal.ZERO;
        }
    }

    /**
     * Áp dụng giá trị mặc định cho status nếu chưa được cài đặt.
     */
    public void applyDefaultHealthStatus() {
        if (this.healthStatus == null || this.healthStatus.isBlank()) {
            this.healthStatus = "ON_TRACK";
        }
    }

    /**
     * Kiểm tra opportunity có đang trong trạng thái thua không.
     */
    public boolean isLost() {
        return "LOST".equalsIgnoreCase(this.healthStatus);
    }

    /**
     * Kiểm tra opportunity có hợp lệ để lưu không.
     */
    public boolean isValid() {
        return this.name != null && !this.name.isBlank();
    }
}
