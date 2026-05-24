package org.example.crm_project.modules.invoices.infrastructure.persistence.entity;

import jakarta.persistence.*; // Đảm bảo import đúng gói jakarta
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "products") // Tên bảng phải khớp 100% với tên trong DB
@Getter
@Setter
public class InvoiceProductViewEntity {

    @Id
    @Column(name = "id") // Đảm bảo tên cột id khớp
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private Double price;

    @Column(name = "sku_code")
    private String skuCode;
}