package org.example.crm_project.modules.contracts.presentation.controller;

import org.example.crm_project.modules.contracts.application.dto.request.*;
import org.example.crm_project.modules.contracts.application.dto.response.*;
import org.example.crm_project.modules.contracts.application.service.ContractService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/v1/contracts")
@RequiredArgsConstructor
@Tag(name = "Contracts", description = "API Quản lý Hợp Đồng - Module 11")
public class ContractController {

    private final ContractService contractService;

    // ─── Tạm thời hardcode userId = 1L; thực tế lấy từ SecurityContext ───────
    private Long currentUserId() {
        return 1L;
    }

    // ─── 1. Danh sách & tìm kiếm ─────────────────────────────────────────────

    @GetMapping
    @Operation(summary = "Lấy danh sách hợp đồng (có lọc & phân trang)")
    public ResponseEntity<ApiResponse<PagedResponse<ContractResponse>>> getContracts(
            @Parameter(description = "Từ khoá tìm kiếm (số HĐ, tên KH)")
            @RequestParam(required = false) String keyword,

            @RequestParam(required = false) String status,
            @RequestParam(required = false) Long ownerId,
            @RequestParam(required = false) Long customerId,

            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDateFrom,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDateTo,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDateFrom,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDateTo,

            @Parameter(description = "Giá trị hợp đồng từ (VD: 10000000)")
            @RequestParam(required = false) java.math.BigDecimal valueFrom,

            @Parameter(description = "Giá trị hợp đồng đến (VD: 500000000)")
            @RequestParam(required = false) java.math.BigDecimal valueTo,

            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        ContractFilterRequest filter = new ContractFilterRequest();
        filter.setKeyword(keyword);
        filter.setOwnerId(ownerId);
        filter.setCustomerId(customerId);
        filter.setStartDateFrom(startDateFrom);
        filter.setStartDateTo(startDateTo);
        filter.setEndDateFrom(endDateFrom);
        filter.setEndDateTo(endDateTo);
        filter.setValueFrom(valueFrom);
        filter.setValueTo(valueTo);
        filter.setPage(page);
        filter.setSize(size);

        if (status != null) {
            try {
                filter.setStatus(org.example.crm_project.modules.contracts.domain.constant.ContractStatus.valueOf(status));
            } catch (IllegalArgumentException ignored) {}
        }

        return ResponseEntity.ok(ApiResponse.ok(contractService.getContracts(filter)));
    }

    // ─── 2. Chi tiết hợp đồng ─────────────────────────────────────────────────

    @GetMapping("/{id}")
    @Operation(summary = "Lấy chi tiết hợp đồng theo ID")
    public ResponseEntity<ApiResponse<ContractResponse>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.ok(contractService.getContractById(id)));
    }

    // ─── 3. Tạo mới ───────────────────────────────────────────────────────────

    @PostMapping
    @Operation(summary = "Tạo hợp đồng mới")
    public ResponseEntity<ApiResponse<ContractResponse>> create(
            @Valid @RequestBody CreateContractRequest request) {
        ContractResponse response = contractService.createContract(request, currentUserId());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.ok("Tạo hợp đồng thành công", response));
    }

    // ─── 4. Cập nhật ──────────────────────────────────────────────────────────

    @PutMapping("/{id}")
    @Operation(summary = "Cập nhật thông tin hợp đồng")
    public ResponseEntity<ApiResponse<ContractResponse>> update(
            @PathVariable Long id,
            @Valid @RequestBody UpdateContractRequest request) {
        return ResponseEntity.ok(ApiResponse.ok("Cập nhật thành công",
                contractService.updateContract(id, request, currentUserId())));
    }

    // ─── 5. Xóa ───────────────────────────────────────────────────────────────

    @DeleteMapping("/{id}")
    @Operation(summary = "Xóa mềm hợp đồng")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        contractService.deleteContract(id, currentUserId());
        return ResponseEntity.ok(ApiResponse.ok("Xóa hợp đồng thành công", null));
    }

    // ─── 6. Cập nhật trạng thái ───────────────────────────────────────────────

    @PatchMapping("/{id}/status")
    @Operation(summary = "Cập nhật trạng thái hợp đồng (DRAFT→SIGNED→ACTIVE→COMPLETED / CANCELLED)")
    public ResponseEntity<ApiResponse<ContractResponse>> updateStatus(
            @PathVariable Long id,
            @Valid @RequestBody UpdateStatusRequest request) {
        return ResponseEntity.ok(ApiResponse.ok("Cập nhật trạng thái thành công",
                contractService.updateStatus(id, request, currentUserId())));
    }

    // ─── 7. Convert từ báo giá ────────────────────────────────────────────────

    @PostMapping("/convert-from-quote")
    @Operation(summary = "Tạo hợp đồng từ Báo giá đã phê duyệt")
    public ResponseEntity<ApiResponse<ContractResponse>> convertFromQuote(
            @Valid @RequestBody ConvertFromQuoteRequest request) {
        ContractResponse response = contractService.convertFromQuote(request, currentUserId());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.ok("Chuyển đổi báo giá thành hợp đồng thành công", response));
    }

    // ─── 8. Bulk Actions ──────────────────────────────────────────────────────

    @PostMapping("/bulk-action")
    @Operation(summary = "Thao tác hàng loạt: ASSIGN (giao) hoặc DELETE (xóa)")
    public ResponseEntity<ApiResponse<Void>> bulkAction(
            @Valid @RequestBody BulkActionRequest request) {
        contractService.bulkAction(request, currentUserId());
        return ResponseEntity.ok(ApiResponse.ok("Thực hiện thao tác hàng loạt thành công", null));
    }

    // ─── 9. Xuất PDF ──────────────────────────────────────────────────────────

    @GetMapping("/{id}/export-pdf")
    @Operation(summary = "Xuất hợp đồng ra file PDF theo template đã chọn")
    public ResponseEntity<byte[]> exportPdf(@PathVariable Long id) {
        byte[] pdf = contractService.exportPdf(id);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "hop-dong-" + id + ".pdf");
        return ResponseEntity.ok().headers(headers).body(pdf);
    }

    // ─── 10. Danh sách template ───────────────────────────────────────────────

    @GetMapping("/templates")
    @Operation(summary = "Lấy danh sách mẫu hợp đồng (document_templates type=CONTRACT)")
    public ResponseEntity<ApiResponse<List<DocumentTemplateResponse>>> getTemplates() {
        return ResponseEntity.ok(ApiResponse.ok(contractService.getContractTemplates()));
    }
}