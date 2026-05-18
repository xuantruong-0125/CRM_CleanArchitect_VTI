package org.example.crm_project.modules.reporting.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserKpiRow {
    private Long userId;
    private String userName;
    private String organizationName;

    /**
     * List of MetricValue
     */
    private List<MetricValue> metricValues = new ArrayList<>();

    /** Doanh số thực tế */
    private MetricValue doanhSo = new MetricValue();

    /** Hoa hồng tính từ doanh số */
    private BigDecimal hoaHong = BigDecimal.ZERO;
}
