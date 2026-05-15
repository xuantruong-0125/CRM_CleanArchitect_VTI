package org.example.crm_project.modules.customers.domain.constant;

import java.math.BigDecimal;

public enum CustomerTier {
    SILVER(1L, "Bạc", BigDecimal.ZERO),
    GOLD(2L, "Vàng", new BigDecimal("50000000.00")),
    PLATINUM(3L, "Kim cương", new BigDecimal("200000000.00"));

    private final Long id;
    private final String name;
    private final BigDecimal minSpending;

    CustomerTier(Long id, String name, BigDecimal minSpending) {
        this.id = id;
        this.name = name;
        this.minSpending = minSpending;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getMinSpending() {
        return minSpending;
    }

    public static CustomerTier fromId(Long id) {
        for (CustomerTier tier : CustomerTier.values()) {
            if (tier.id.equals(id)) {
                return tier;
            }
        }
        return SILVER;
    }
}
