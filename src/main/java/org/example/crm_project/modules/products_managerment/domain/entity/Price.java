package org.example.crm_project.modules.products_managerment.domain.entity;

import org.example.crm_project.modules.products_managerment.domain.entity.Product;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

public class Price {
    private Long id;
    private BigDecimal basePrice;
    private BigDecimal taxRate;
    private LocalDateTime effectiveFrom;
    private LocalDateTime effectiveTo;
    private BigDecimal finalPrice;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
    private Long createdBy;
    private Long updatedBy;
    private Boolean isActive;
    private Product product;

    public Price() {
    }


    public static Price create(BigDecimal basePrice, BigDecimal taxRate,
                               LocalDateTime effectiveFrom, LocalDateTime effectiveTo,
                               Product product, Long createdBy) {
        Price p = new Price();
        p.basePrice = basePrice;
        p.taxRate = taxRate;
        p.effectiveFrom = effectiveFrom;
        p.effectiveTo = effectiveTo;
        p.product = product;
        p.createdBy = createdBy;
        p.createdAt = LocalDateTime.now();
        p.isActive = true;
        p.finalPrice = p.calculateFinalPrice();
        return p;
    }

    public BigDecimal calculateFinalPrice() {
        if (basePrice == null) return BigDecimal.ZERO;
    
        BigDecimal tax = (taxRate != null) ? taxRate : BigDecimal.ZERO;

        BigDecimal taxRateAsDecimal = tax.divide(new BigDecimal("100")); 

        BigDecimal finalPrice = basePrice.multiply(BigDecimal.ONE.add(taxRateAsDecimal));


        return finalPrice.setScale(0, RoundingMode.HALF_UP);
    }

    public boolean isCurrentlyEffective() {
        LocalDateTime now = LocalDateTime.now();
        return Boolean.TRUE.equals(isActive)
                && deletedAt == null
                && effectiveFrom != null
                && !effectiveFrom.isAfter(now)
                && (effectiveTo == null || effectiveTo.isAfter(now));
    }

    public void softDelete(Long deletedBy) {
        this.deletedAt = LocalDateTime.now();
        this.updatedBy = deletedBy;
        this.isActive = false;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(BigDecimal basePrice) {
        this.basePrice = basePrice;
        this.finalPrice = calculateFinalPrice(); // tự cập nhật
    }

    public BigDecimal getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
        this.finalPrice = calculateFinalPrice(); // tự cập nhật
    }

    public LocalDateTime getEffectiveFrom() {
        return effectiveFrom;
    }

    public void setEffectiveFrom(LocalDateTime effectiveFrom) {
        this.effectiveFrom = effectiveFrom;
    }

    public LocalDateTime getEffectiveTo() {
        return effectiveTo;
    }

    public void setEffectiveTo(LocalDateTime effectiveTo) {
        this.effectiveTo = effectiveTo;
    }

    public BigDecimal getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(BigDecimal finalPrice) {
        this.finalPrice = finalPrice;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Long getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}