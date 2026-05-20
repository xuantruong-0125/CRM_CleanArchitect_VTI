package org.example.crm_project.modules.opportunity_management.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO nhận dữ liệu Pipeline từ form.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PipelineRequest {
    private Integer id;
    private String name;
}
