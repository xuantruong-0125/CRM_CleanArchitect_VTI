package org.example.crm_project.modules.contracts.domain.exception;

import org.example.crm_project.modules.contracts.domain.constant.ContractStatus;

public class InvalidContractStatusTransitionException extends RuntimeException {
    public InvalidContractStatusTransitionException(ContractStatus from, ContractStatus to) {
        super(String.format("Không thể chuyển trạng thái hợp đồng từ [%s] sang [%s]",
                from.getDisplayName(), to.getDisplayName()));
    }
}
