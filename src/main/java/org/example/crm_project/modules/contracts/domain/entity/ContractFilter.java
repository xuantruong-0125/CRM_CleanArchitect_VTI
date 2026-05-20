package org.example.crm_project.modules.contracts.domain.entity;

import org.example.crm_project.modules.contracts.domain.constant.ContractStatus;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Value object đóng gói các tiêu chí tìm kiếm / lọc hợp đồng.
 */
@Getter
@Builder
public class ContractFilter {
    private String     keyword;
    private ContractStatus status;
    private Long       ownerId;
    private Long       customerId;
    private LocalDate  startDateFrom;
    private LocalDate  startDateTo;
    private LocalDate  endDateFrom;
    private LocalDate  endDateTo;
    private BigDecimal valueFrom;   // lọc giá trị hợp đồng từ
    private BigDecimal valueTo;     // lọc giá trị hợp đồng đến
    private int        page;
    private int        size;
}