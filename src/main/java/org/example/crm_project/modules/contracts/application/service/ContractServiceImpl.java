package org.example.crm_project.modules.contracts.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.crm_project.modules.contracts.application.dto.request.*;
import org.example.crm_project.modules.contracts.application.dto.response.*;
import org.example.crm_project.modules.contracts.domain.constant.ContractStatus;
import org.example.crm_project.modules.contracts.domain.entity.Contract;
import org.example.crm_project.modules.contracts.domain.entity.ContractFilter;
import org.example.crm_project.modules.contracts.domain.exception.ContractNotFoundException;
import org.example.crm_project.modules.contracts.domain.exception.InvalidContractStatusTransitionException;
import org.example.crm_project.modules.contracts.domain.repository.ContractRepository;
import org.example.crm_project.modules.contracts.domain.repository.DocumentTemplateRepository;
import org.example.crm_project.modules.contracts.infrastructure.client.CustomerClient;
import org.example.crm_project.modules.contracts.infrastructure.client.PdfExportService;
import org.example.crm_project.modules.contracts.infrastructure.client.QuoteClient;
import org.example.crm_project.modules.contracts.infrastructure.persistence.entity.DocumentTemplateJpaEntity;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ContractServiceImpl implements ContractService {

    private final ContractRepository       contractRepository;
    private final DocumentTemplateRepository templateRepository;
    private final CustomerClient           customerClient;
    private final QuoteClient              quoteClient;
    private final PdfExportService         pdfExportService;

    // ─── Create ───────────────────────────────────────────────────────────────

    @Override
    @Transactional
    public ContractResponse createContract(CreateContractRequest req, Long currentUserId) {
        Contract contract = Contract.builder()
                .customerId(req.getCustomerId())
                .contractNumber(req.getContractNumber())
                .quoteId(req.getQuoteId())
                .templateId(req.getTemplateId())
                .contractValue(req.getContractValue())
                .currencyCode(req.getCurrencyCode() != null ? req.getCurrencyCode() : "VND")
                .exchangeRate(req.getExchangeRate() != null ? req.getExchangeRate() : BigDecimal.ONE)
                .status(ContractStatus.DRAFT)
                .startDate(req.getStartDate())
                .endDate(req.getEndDate())
                .ownerId(req.getOwnerId())
                .createdBy(currentUserId)
                .updatedBy(currentUserId)
                .build();

        if (contract.getContractNumber() == null || contract.getContractNumber().isBlank()) {
            contract.setContractNumber(generateContractNumber());
        } else if (contractRepository.existsByContractNumber(contract.getContractNumber())) {
            throw new IllegalArgumentException(
                    "Số hợp đồng [" + contract.getContractNumber() + "] đã tồn tại");
        }

        enrichCustomerName(contract);
        Contract saved = contractRepository.save(contract);
        log.info("Tạo hợp đồng: {} bởi user {}", saved.getContractNumber(), currentUserId);
        return toResponse(saved);
    }

    // ─── Get by ID ────────────────────────────────────────────────────────────

    @Override
    public ContractResponse getContractById(Long id) {
        Contract contract = contractRepository.findById(id)
                .orElseThrow(() -> new ContractNotFoundException(id));
        enrichCustomerName(contract);
        return toResponse(contract);
    }

    // ─── Update ───────────────────────────────────────────────────────────────

    @Override
    @Transactional
    public ContractResponse updateContract(Long id, UpdateContractRequest req, Long currentUserId) {
        Contract contract = contractRepository.findById(id)
                .orElseThrow(() -> new ContractNotFoundException(id));

        if (req.getContractNumber() != null
                && !req.getContractNumber().equals(contract.getContractNumber())
                && contractRepository.existsByContractNumber(req.getContractNumber())) {
            throw new IllegalArgumentException(
                    "Số hợp đồng [" + req.getContractNumber() + "] đã tồn tại");
        }

        if (req.getContractNumber() != null) contract.setContractNumber(req.getContractNumber());
        if (req.getCustomerId()     != null) contract.setCustomerId(req.getCustomerId());
        if (req.getTemplateId()     != null) contract.setTemplateId(req.getTemplateId());
        if (req.getContractValue()  != null) contract.setContractValue(req.getContractValue());
        if (req.getCurrencyCode()   != null) contract.setCurrencyCode(req.getCurrencyCode());
        if (req.getExchangeRate()   != null) contract.setExchangeRate(req.getExchangeRate());
        if (req.getStartDate()      != null) contract.setStartDate(req.getStartDate());
        if (req.getEndDate()        != null) contract.setEndDate(req.getEndDate());
        if (req.getOwnerId()        != null) contract.setOwnerId(req.getOwnerId());
        contract.setUpdatedBy(currentUserId);

        enrichCustomerName(contract);
        Contract saved = contractRepository.save(contract);
        log.info("Cập nhật hợp đồng ID={} bởi user {}", id, currentUserId);
        return toResponse(saved);
    }

    // ─── Delete ───────────────────────────────────────────────────────────────

    @Override
    @Transactional
    public void deleteContract(Long id, Long currentUserId) {
        Contract contract = contractRepository.findById(id)
                .orElseThrow(() -> new ContractNotFoundException(id));
        if (!contract.isCancellable()) {
            throw new IllegalStateException(
                    "Không thể xóa hợp đồng ở trạng thái: " + contract.getStatus().getDisplayName());
        }
        contractRepository.softDelete(id, currentUserId);
        log.info("Xóa hợp đồng ID={} bởi user {}", id, currentUserId);
    }

    // ─── List / Search ────────────────────────────────────────────────────────

    @Override
    public PagedResponse<ContractResponse> getContracts(ContractFilterRequest req) {
        ContractFilter filter = ContractFilter.builder()
                .keyword(req.getKeyword())
                .status(req.getStatus())
                .ownerId(req.getOwnerId())
                .customerId(req.getCustomerId())
                .startDateFrom(req.getStartDateFrom())
                .startDateTo(req.getStartDateTo())
                .endDateFrom(req.getEndDateFrom())
                .endDateTo(req.getEndDateTo())
                .valueFrom(req.getValueFrom())
                .valueTo(req.getValueTo())
                .page(req.getPage())
                .size(Math.min(req.getSize(), 100))
                .build();

        Page<ContractResponse> page = contractRepository.findAll(filter).map(this::toResponse);
        return PagedResponse.of(page);
    }

    // ─── Update status ────────────────────────────────────────────────────────

    @Override
    @Transactional
    public ContractResponse updateStatus(Long id, UpdateStatusRequest req, Long currentUserId) {
        Contract contract = contractRepository.findById(id)
                .orElseThrow(() -> new ContractNotFoundException(id));
        if (!contract.canTransitionTo(req.getStatus())) {
            throw new InvalidContractStatusTransitionException(contract.getStatus(), req.getStatus());
        }
        contract.setStatus(req.getStatus());
        contract.setUpdatedBy(currentUserId);
        Contract saved = contractRepository.save(contract);
        log.info("Cập nhật trạng thái HĐ ID={} → {} bởi user {}", id, req.getStatus(), currentUserId);
        return toResponse(saved);
    }

    // ─── Convert from quote ───────────────────────────────────────────────────

    @Override
    @Transactional
    public ContractResponse convertFromQuote(ConvertFromQuoteRequest req, Long currentUserId) {
        QuoteClient.QuoteData quote = quoteClient.getQuoteById(req.getQuoteId());
        if (!quote.isApproved()) {
            throw new IllegalStateException("Chỉ có thể tạo hợp đồng từ báo giá đã được phê duyệt");
        }
        Contract contract = Contract.builder()
                .quoteId(req.getQuoteId())
                .customerId(quote.getCustomerId())
                .contractValue(quote.getTotalAmount())
                .currencyCode(quote.getCurrencyCode())
                .exchangeRate(quote.getExchangeRate())
                .status(ContractStatus.DRAFT)
                .startDate(req.getStartDate() != null ? req.getStartDate() : LocalDate.now())
                .endDate(req.getEndDate())
                .ownerId(req.getOwnerId())
                .contractNumber(generateContractNumber())
                .createdBy(currentUserId)
                .updatedBy(currentUserId)
                .build();

        enrichCustomerName(contract);
        Contract saved = contractRepository.save(contract);
        log.info("Convert báo giá {} → hợp đồng {} bởi user {}",
                req.getQuoteId(), saved.getContractNumber(), currentUserId);
        return toResponse(saved);
    }

    // ─── Bulk action ──────────────────────────────────────────────────────────

    @Override
    @Transactional
    public void bulkAction(BulkActionRequest req, Long currentUserId) {
        List<Contract> contracts = contractRepository.findAllByIds(req.getIds());
        switch (req.getAction()) {
            case ASSIGN -> {
                if (req.getAssignToUserId() == null)
                    throw new IllegalArgumentException("Vui lòng chỉ định người phụ trách");
                contracts.forEach(c -> {
                    c.setOwnerId(req.getAssignToUserId());
                    c.setUpdatedBy(currentUserId);
                    contractRepository.save(c);
                });
            }
            case DELETE -> contracts.forEach(c -> {
                if (c.isCancellable()) contractRepository.softDelete(c.getId(), currentUserId);
            });
        }
    }

    // ─── Export PDF ───────────────────────────────────────────────────────────

    @Override
    public byte[] exportPdf(Long contractId) {
        Contract contract = contractRepository.findById(contractId)
                .orElseThrow(() -> new ContractNotFoundException(contractId));
        if (contract.getTemplateId() == null)
            throw new IllegalStateException("Hợp đồng chưa được gán template để xuất PDF");

        DocumentTemplateJpaEntity template = templateRepository.findById(contract.getTemplateId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Không tìm thấy template ID: " + contract.getTemplateId()));

        enrichCustomerName(contract);
        return pdfExportService.generatePdf(buildHtml(template.getContentHtml(), contract));
    }

    // ─── Templates ───────────────────────────────────────────────────────────

    @Override
    public List<DocumentTemplateResponse> getContractTemplates() {
        return templateRepository.findAllContractTemplates().stream()
                .map(t -> DocumentTemplateResponse.builder()
                        .id(t.getId()).name(t.getName())
                        .type(t.getType()).isActive(t.getIsActive())
                        .build())
                .toList();
    }

    // ─── Private helpers ──────────────────────────────────────────────────────

    private void enrichCustomerName(Contract contract) {
        if (contract.getCustomerId() != null) {
            try {
                contract.setCustomerName(customerClient.getCustomerName(contract.getCustomerId()));
            } catch (Exception e) {
                log.warn("Không lấy được tên KH ID={}: {}", contract.getCustomerId(), e.getMessage());
            }
        }
    }

    private String generateContractNumber() {
        String prefix = "HD-" + LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMM")) + "-";
        int next = contractRepository.getNextSequenceForPrefix(prefix);
        String candidate;
        int attempt = 0;
        do {
            candidate = String.format("%s%04d", prefix, next + attempt++);
        } while (contractRepository.existsByContractNumber(candidate) && attempt < 100);
        return candidate;
    }

    private String buildHtml(String html, Contract c) {
        return html
                .replace("{{contract_number}}", safe(c.getContractNumber()))
                .replace("{{customer_name}}",   safe(c.getCustomerName()))
                .replace("{{contract_value}}",  c.getContractValue() != null
                        ? c.getContractValue().toPlainString() : "")
                .replace("{{currency_code}}",   safe(c.getCurrencyCode()))
                .replace("{{start_date}}",       c.getStartDate() != null
                        ? c.getStartDate().toString() : "")
                .replace("{{end_date}}",         c.getEndDate() != null
                        ? c.getEndDate().toString() : "")
                .replace("{{status}}",           c.getStatus() != null
                        ? c.getStatus().getDisplayName() : "");
    }

    private static String safe(String s) { return s != null ? s : ""; }

    // ─── toResponse — manual mapping thay MapStruct ──────────────────────────

    private ContractResponse toResponse(Contract c) {
        return ContractResponse.builder()
                .id(c.getId())
                .contractNumber(c.getContractNumber())
                .customerId(c.getCustomerId())
                .customerName(c.getCustomerName())
                .quoteId(c.getQuoteId())
                .templateId(c.getTemplateId())
                .contractValue(c.getContractValue())
                .currencyCode(c.getCurrencyCode())
                .exchangeRate(c.getExchangeRate())
                .status(c.getStatus())
                .statusDisplayName(c.getStatus() != null ? c.getStatus().getDisplayName() : null)
                .startDate(c.getStartDate())
                .endDate(c.getEndDate())
                .ownerId(c.getOwnerId())
                .ownerName(c.getOwnerName())
                .createdAt(c.getCreatedAt())
                .updatedAt(c.getUpdatedAt())
                .build();
    }
}