package org.example.crm_project.modules.contracts.domain.entity;

import org.example.crm_project.modules.contracts.domain.constant.ContractStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Domain entity – thuần túy business logic, không phụ thuộc JPA.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Contract {

    private Long id;
    private String contractNumber;
    private Long customerId;
    private String customerName;
    private Long quoteId;
    private Long templateId;
    private BigDecimal contractValue;
    private String currencyCode;
    private BigDecimal exchangeRate;
    private ContractStatus status;
    private LocalDate startDate;
    private LocalDate endDate;
    private Long ownerId;
    private String ownerName;
    private Long createdBy;
    private Long updatedBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;

    public boolean isActive() {
        return ContractStatus.ACTIVE.equals(this.status);
    }

    public boolean isCancellable() {
        return !ContractStatus.COMPLETED.equals(this.status)
                && !ContractStatus.CANCELLED.equals(this.status);
    }

    public boolean canTransitionTo(ContractStatus newStatus) {
        return switch (this.status) {
            case DRAFT -> newStatus == ContractStatus.SIGNED || newStatus == ContractStatus.CANCELLED;
            case SIGNED -> newStatus == ContractStatus.ACTIVE || newStatus == ContractStatus.CANCELLED;
            case ACTIVE -> newStatus == ContractStatus.COMPLETED || newStatus == ContractStatus.CANCELLED;
            default -> false;
        };
    }
}
