package org.example.crm_project.modules.contracts.application.dto.response;

import org.example.crm_project.modules.contracts.domain.constant.ContractStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
public class ContractResponse {

    private Long id;
    private String contractNumber;
    private Long customerId;
    private String customerName;
    private Long quoteId;
    private Long templateId;
    private BigDecimal contractValue;
    private String currencyCode;
    private BigDecimal exchangeRate;
    private ContractStatus status;
    private String statusDisplayName;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    private Long ownerId;
    private String ownerName;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;
}
