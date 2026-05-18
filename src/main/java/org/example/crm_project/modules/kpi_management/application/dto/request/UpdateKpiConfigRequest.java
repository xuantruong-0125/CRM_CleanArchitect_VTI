package org.example.crm_project.modules.kpi_management.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateKpiConfigRequest {
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private String status;
    private String description;
    private Integer updatedBy;
    private List<KpiTargetRequest> targets = new ArrayList<>();
    private List<KpiAssignmentRequest> assignments = new ArrayList<>();
}
