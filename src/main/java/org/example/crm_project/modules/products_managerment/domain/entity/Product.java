package org.example.crm_project.modules.products_managerment.domain.entity;

import org.example.crm_project.modules.products_managerment.domain.entity.Category;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class Product {
    private Long id;
    private String skuCode;
    private String name;
    private String imageUrl;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
    private Long createdBy;
    private Long updatedBy;
    private Boolean isActive;
    private Category category;
    private List<Price> prices = new ArrayList<>();

    public Product() {}

    public Product(Long id, String skuCode, String name, String imageUrl,
                   String description, LocalDateTime createdAt, LocalDateTime updatedAt,
                   LocalDateTime deletedAt, Long createdBy, Long updatedBy,
                   Boolean isActive, Category category) {
        this.id = id;
        this.skuCode = skuCode;
        this.name = name;
        this.imageUrl = imageUrl;
        this.description = description;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
        this.isActive = isActive;
        this.category = category;
    }


    public static Product create(String skuCode, String name, String imageUrl,
                                 String description, Category category, Long createdBy) {
        Product p = new Product();
        p.skuCode = skuCode;
        p.name = name;
        p.imageUrl = imageUrl;
        p.description = description;
        p.category = category;
        p.createdBy = createdBy;
        p.createdAt = LocalDateTime.now();
        p.isActive = true;
        return p;
    }

    public void update(String name, String imageUrl, String description, Long updatedBy) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.description = description;
        this.updatedBy = updatedBy;
        this.updatedAt = LocalDateTime.now();
    }

    public void softDelete(Long deletedBy) {
        this.deletedAt = LocalDateTime.now();
        this.updatedBy = deletedBy;
        this.isActive = false;
    }

    public boolean isDeleted() {
        return this.deletedAt != null;
    }

    public void addPrice(Price price) {
        this.prices.add(price);
    }

    public List<Price> getPrices() {
        return Collections.unmodifiableList(prices);
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getSkuCode() { return skuCode; }
    public void setSkuCode(String skuCode) { this.skuCode = skuCode; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public LocalDateTime getDeletedAt() { return deletedAt; }
    public void setDeletedAt(LocalDateTime deletedAt) { this.deletedAt = deletedAt; }

    public Long getCreatedBy() { return createdBy; }
    public void setCreatedBy(Long createdBy) { this.createdBy = createdBy; }

    public Long getUpdatedBy() { return updatedBy; }
    public void setUpdatedBy(Long updatedBy) { this.updatedBy = updatedBy; }

    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }

    public Category getCategory() { return category; }
    public void setCategory(Category category) { this.category = category; }
}