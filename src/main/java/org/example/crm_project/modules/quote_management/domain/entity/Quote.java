package org.example.crm_project.modules.quote_management.domain.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Domain entity cho báo giá.
 * POJO thuần túy - không có JPA annotation, chứa business logic.
 */
public class Quote {

    private Integer id;
    private String quoteNumber;
    private Integer customerId;
    private Integer opportunityId;
    private Integer statusId;
    private BigDecimal totalAmount;
    private String currencyCode;
    private BigDecimal exchangeRate;
    private LocalDate validUntil;
    private Integer templateId;
    private Integer createdBy;
    private Integer updatedBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;

    // ===== Constructor tạo mới =====
    public Quote(String quoteNumber, Integer customerId, Integer opportunityId,
                 Integer statusId, String currencyCode, BigDecimal exchangeRate,
                 LocalDate validUntil, Integer templateId, Integer createdBy) {
        validateQuoteNumber(quoteNumber);
        validateCustomer(customerId);
        this.quoteNumber = quoteNumber;
        this.customerId = customerId;
        this.opportunityId = opportunityId;
        this.statusId = statusId;
        this.totalAmount = BigDecimal.ZERO;
        this.currencyCode = currencyCode != null ? currencyCode : "VND";
        this.exchangeRate = exchangeRate != null ? exchangeRate : BigDecimal.ONE;
        this.validUntil = validUntil;
        this.templateId = templateId;
        this.createdBy = createdBy;
    }

    // ===== Constructor load từ DB =====
    public Quote(Integer id, String quoteNumber, Integer customerId, Integer opportunityId,
                 Integer statusId, BigDecimal totalAmount, String currencyCode,
                 BigDecimal exchangeRate, LocalDate validUntil, Integer templateId,
                 Integer createdBy, Integer updatedBy, LocalDateTime createdAt,
                 LocalDateTime updatedAt, LocalDateTime deletedAt) {
        this.id = id;
        this.quoteNumber = quoteNumber;
        this.customerId = customerId;
        this.opportunityId = opportunityId;
        this.statusId = statusId;
        this.totalAmount = totalAmount != null ? totalAmount : BigDecimal.ZERO;
        this.currencyCode = currencyCode;
        this.exchangeRate = exchangeRate;
        this.validUntil = validUntil;
        this.templateId = templateId;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }

    // ===== Getter =====
    public Integer getId() { return id; }
    public String getQuoteNumber() { return quoteNumber; }
    public Integer getCustomerId() { return customerId; }
    public Integer getOpportunityId() { return opportunityId; }
    public Integer getStatusId() { return statusId; }
    public BigDecimal getTotalAmount() { return totalAmount; }
    public String getCurrencyCode() { return currencyCode; }
    public BigDecimal getExchangeRate() { return exchangeRate; }
    public LocalDate getValidUntil() { return validUntil; }
    public Integer getTemplateId() { return templateId; }
    public Integer getCreatedBy() { return createdBy; }
    public Integer getUpdatedBy() { return updatedBy; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public LocalDateTime getDeletedAt() { return deletedAt; }
    public boolean isDeleted() { return deletedAt != null; }

    // ===== Business Logic =====

    /**
     * Cập nhật thông tin báo giá
     */
    public void update(String quoteNumber, Integer customerId, Integer opportunityId,
                       Integer statusId, String currencyCode, BigDecimal exchangeRate,
                       LocalDate validUntil, Integer templateId, Integer updatedBy) {
        validateQuoteNumber(quoteNumber);
        validateCustomer(customerId);
        this.quoteNumber = quoteNumber;
        this.customerId = customerId;
        this.opportunityId = opportunityId;
        this.statusId = statusId;
        this.currencyCode = currencyCode != null ? currencyCode : "VND";
        this.exchangeRate = exchangeRate != null ? exchangeRate : BigDecimal.ONE;
        this.validUntil = validUntil;
        this.templateId = templateId;
        this.updatedBy = updatedBy;
    }

    /**
     * Soft delete: đánh dấu đã xóa
     */
    public void softDelete() {
        if (this.deletedAt != null) {
            throw new IllegalStateException("Quote is already deleted");
        }
        this.deletedAt = LocalDateTime.now();
    }

    // ===== Validation =====
    private void validateQuoteNumber(String quoteNumber) {
        if (quoteNumber == null || quoteNumber.isBlank()) {
            throw new IllegalArgumentException("Số báo giá không được để trống");
        }
        if (quoteNumber.length() > 50) {
            throw new IllegalArgumentException("Số báo giá tối đa 50 ký tự");
        }
    }

    private void validateCustomer(Integer customerId) {
        if (customerId == null) {
            throw new IllegalArgumentException("Vui lòng chọn khách hàng");
        }
    }
}
