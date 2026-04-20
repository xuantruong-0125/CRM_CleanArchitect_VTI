package org.example.crm_project.modules.leads.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LeadReferenceOption {

    private Long id;
    private String code;
    private String name;
}