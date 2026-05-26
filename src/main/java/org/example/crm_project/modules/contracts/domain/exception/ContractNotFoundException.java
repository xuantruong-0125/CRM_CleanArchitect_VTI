package org.example.crm_project.modules.contracts.domain.exception;

public class ContractNotFoundException extends RuntimeException {
    public ContractNotFoundException(Long id) {
        super("Không tìm thấy hợp đồng với ID: " + id);
    }
    public ContractNotFoundException(String contractNumber) {
        super("Không tìm thấy hợp đồng với số: " + contractNumber);
    }
}
