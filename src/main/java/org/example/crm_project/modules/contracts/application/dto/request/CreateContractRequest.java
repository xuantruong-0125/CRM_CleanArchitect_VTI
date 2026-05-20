package org.example.crm_project.modules.contracts.application.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class CreateContractRequest {

    @NotNull(message = "Khách hàng không được để trống")
    private Long customerId;

    @Size(max = 50, message = "Số hợp đồng tối đa 50 ký tự")
    private String contractNumber;

    private Long quoteId;

    private Long templateId;

    @DecimalMin(value = "0", inclusive = false, message = "Giá trị hợp đồng phải lớn hơn 0")
    private BigDecimal contractValue;

    @Size(max = 10)
    private String currencyCode = "VND";

    private BigDecimal exchangeRate = BigDecimal.ONE;

    @NotNull(message = "Ngày bắt đầu không được để trống")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    private Long ownerId;
}
