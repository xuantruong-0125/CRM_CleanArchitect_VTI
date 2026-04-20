package org.example.crm_project.modules.quote_management.domain.entity;

import org.example.crm_project.modules.quote_management.domain.constant.ProductType;

/**
 * Domain entity cho sản phẩm/dịch vụ.
 * POJO thuần túy - không có JPA annotation.
 */
public class Product {

    private Integer id;
    private String skuCode;
    private String name;
    private ProductType type;
    private Boolean isActive;

    // ===== Constructor load từ DB =====
    public Product(Integer id, String skuCode, String name, ProductType type, Boolean isActive) {
        this.id = id;
        this.skuCode = skuCode;
        this.name = name;
        this.type = type;
        this.isActive = isActive;
    }

    // ===== Getter =====
    public Integer getId() { return id; }
    public String getSkuCode() { return skuCode; }
    public String getName() { return name; }
    public ProductType getType() { return type; }
    public Boolean getIsActive() { return isActive; }
}
