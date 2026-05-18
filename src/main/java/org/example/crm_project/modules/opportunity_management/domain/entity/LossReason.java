package org.example.crm_project.modules.opportunity_management.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Domain Entity – LossReason (lý do thua thương vụ).
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LossReason {
    private Integer id;
    private String name;
    private String description;
    private Boolean isActive;
}
