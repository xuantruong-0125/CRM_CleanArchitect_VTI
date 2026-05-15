package org.example.crm_project.modules.products_managerment.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
@Entity(name = "ProductsProductEntity")
@Table(name = "products")
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;
    @Column(name = "sku_code")
    String skuCode;
    @Column(name = "name")
    String name;
    @Column(name = "image_url")
    String imageUrl;
    @Column(name = "description")
    String description;
    @Column(name = "created_at")
    LocalDateTime createdAt;
    @Column(name = "updated_at")
    LocalDateTime updatedAt;
    @Column(name = "deleted_at")
    LocalDateTime deletedAt;
    @Column(name = "created_by")
    Long createdBy;
    @Column(name = "updated_by")
    Long updatedBy;
    @Column(name = "is_active")
    Boolean isActive;

    @ManyToOne
    @JoinColumn(name = "category_id")
    CategoryEntity categoryEntity;

    @OneToMany(mappedBy = "productEntity")
    List<PriceEntity> prices;
}
