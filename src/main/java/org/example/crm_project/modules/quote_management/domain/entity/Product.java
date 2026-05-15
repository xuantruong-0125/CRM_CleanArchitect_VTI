package org.example.crm_project.modules.quote_management.domain.entity;

import org.example.crm_project.modules.quote_management.domain.constant.ProductType;
import java.math.BigDecimal;

/**
 * Domain entity cho sản phẩm/dịch vụ.
 * POJO thuần túy - không có JPA annotation.
 */
public class Product {

    private Integer id;
    private String skuCode;
    private String name;
    private BigDecimal price;
    private ProductType type;
    private Boolean isActive;

    // ===== Constructor load từ DB =====
    public Product(Integer id, String skuCode, String name, BigDecimal price, ProductType type, Boolean isActive) {
        this.id = id;
        this.skuCode = skuCode;
        this.name = name;
        this.price = price;
        this.type = type;
        this.isActive = isActive;
    }

    // ===== Getter =====
    public Integer getId() { return id; }
    public String getSkuCode() { return skuCode; }
    public String getName() { return name; }
    public ProductType getType() { return type; }
    public BigDecimal getPrice() { return price; }
    public Boolean getIsActive() { return isActive; }
}
