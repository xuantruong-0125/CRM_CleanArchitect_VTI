package org.example.crm_project.modules.kpi_management.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class KpiTargetRequest {
    private String metricType;
    private BigDecimal targetValue;
}
