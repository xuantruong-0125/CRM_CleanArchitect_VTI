package org.example.crm_project.modules.contracts.application.dto.request;

import org.example.crm_project.modules.contracts.domain.constant.ContractStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class ContractFilterRequest {

    private String keyword;
    private ContractStatus status;
    private Long ownerId;
    private Long customerId;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDateFrom;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDateTo;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDateFrom;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDateTo;

    private BigDecimal valueFrom;   // giá trị hợp đồng từ (triệu đồng hoặc đơn vị gốc)
    private BigDecimal valueTo;     // giá trị hợp đồng đến

    private int page = 0;
    private int size = 10;
}