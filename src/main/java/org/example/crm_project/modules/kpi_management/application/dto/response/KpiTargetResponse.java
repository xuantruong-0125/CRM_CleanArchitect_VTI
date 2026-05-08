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
public class KpiTargetResponse {
    private Integer id;
    private String metricType;
    private BigDecimal targetValue;
}
