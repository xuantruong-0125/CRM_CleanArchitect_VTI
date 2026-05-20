package org.example.crm_project.modules.contracts.application.service;

import org.example.crm_project.modules.contracts.application.dto.request.*;
import org.example.crm_project.modules.contracts.application.dto.response.*;

import java.util.List;

public interface ContractService {

    ContractResponse createContract(CreateContractRequest request, Long currentUserId);

    ContractResponse getContractById(Long id);

    ContractResponse updateContract(Long id, UpdateContractRequest request, Long currentUserId);

    void deleteContract(Long id, Long currentUserId);

    PagedResponse<ContractResponse> getContracts(ContractFilterRequest filter);

    ContractResponse updateStatus(Long id, UpdateStatusRequest request, Long currentUserId);

    ContractResponse convertFromQuote(ConvertFromQuoteRequest request, Long currentUserId);

    void bulkAction(BulkActionRequest request, Long currentUserId);

    byte[] exportPdf(Long contractId);

    List<DocumentTemplateResponse> getContractTemplates();
}
