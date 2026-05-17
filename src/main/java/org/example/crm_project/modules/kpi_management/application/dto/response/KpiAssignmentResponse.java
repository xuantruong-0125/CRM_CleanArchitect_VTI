package org.example.crm_project.modules.kpi_management.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KpiAssignmentResponse {
    private Integer id;
    private Integer userId;
    private Integer organizationId;
    private BigDecimal commissionPercent;
}
