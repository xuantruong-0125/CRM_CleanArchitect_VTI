package org.example.crm_project.modules.contracts.application.dto.request;

import org.example.crm_project.modules.contracts.domain.constant.ContractStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateStatusRequest {

    @NotNull(message = "Trạng thái không được để trống")
    private ContractStatus status;
}
