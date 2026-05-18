package org.example.crm_project.modules.contracts.application.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ConvertFromQuoteRequest {

    @NotNull(message = "ID báo giá không được để trống")
    private Long quoteId;

    private LocalDate startDate;
    private LocalDate endDate;
    private Long ownerId;
}
