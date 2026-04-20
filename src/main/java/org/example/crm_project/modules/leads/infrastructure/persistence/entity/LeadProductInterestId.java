package org.example.crm_project.modules.leads.infrastructure.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LeadProductInterestId implements Serializable {

    private Long leadId;
    private Long productId;
}