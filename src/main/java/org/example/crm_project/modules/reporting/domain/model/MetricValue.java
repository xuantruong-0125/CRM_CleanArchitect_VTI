package org.example.crm_project.modules.reporting.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MetricValue {
    private BigDecimal actual = BigDecimal.ZERO;
    private BigDecimal target = BigDecimal.ZERO;

    public double getProgress() {
        if (target == null || target.compareTo(BigDecimal.ZERO) <= 0) {
            return 0;
        }
        return actual.multiply(BigDecimal.valueOf(100))
                .divide(target, 2, RoundingMode.HALF_UP)
                .doubleValue();
    }
}
