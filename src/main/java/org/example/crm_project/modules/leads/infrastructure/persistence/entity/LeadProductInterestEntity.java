package org.example.crm_project.modules.leads.infrastructure.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "lead_product_interests")
@IdClass(LeadProductInterestId.class)
@Getter
@Setter
public class LeadProductInterestEntity {

    @Id
    @Column(name = "lead_id")
    private Long leadId;

    @Id
    @Column(name = "product_id")
    private Long productId;
}