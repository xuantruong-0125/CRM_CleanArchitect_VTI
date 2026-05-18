package org.example.crm_project.modules.customers.presentation.controller;

import org.example.crm_project.modules.customers.application.dto.request.CreateCustomerDTO;
import org.example.crm_project.modules.customers.application.dto.response.CustomerResponseDTO;
import org.example.crm_project.modules.customers.application.dto.request.UpdateCustomerDTO;
import org.example.crm_project.modules.customers.application.service.CustomerService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

/**
 * Controller: CustomerController
 * REST endpoints cho quản lý khách hàng
 */
@RestController
@RequestMapping("/api/customers")
@CrossOrigin(origins = "*", maxAge = 3600)
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    /**
     * POST /api/customers - Tạo khách hàng mới
     */
    @PostMapping
    public ResponseEntity<CustomerResponseDTO> createCustomer(@Valid @RequestBody CreateCustomerDTO createDTO) {
        CustomerResponseDTO result = customerService.createCustomer(createDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    /**
     * GET /api/customers/{id} - Lấy thông tin khách hàng
     */
    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponseDTO> getCustomerById(@PathVariable Long id) {
        CustomerResponseDTO result = customerService.getCustomerById(id);
        return ResponseEntity.ok(result);
    }

    /**
     * PUT /api/customers/{id} - Cập nhật khách hàng
     */
    @PutMapping("/{id}")
    public ResponseEntity<CustomerResponseDTO> updateCustomer(
            @PathVariable Long id,
            @Valid @RequestBody UpdateCustomerDTO updateDTO) {
        CustomerResponseDTO result = customerService.updateCustomer(id, updateDTO);
        return ResponseEntity.ok(result);
    }

    /**
     * DELETE /api/customers/{id} - Xóa khách hàng (soft delete)
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * GET /api/customers - Lấy danh sách khách hàng phân trang
     * Param: page (default 0), size (default 20), sort (default "createdAt,desc")
     */
    @GetMapping
    public ResponseEntity<Page<CustomerResponseDTO>> getAllCustomers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDirection) {
        
        Sort.Direction direction = Sort.Direction.fromString(sortDirection.toUpperCase());
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
        Page<CustomerResponseDTO> result = customerService.getAllCustomers(pageable);
        
        return ResponseEntity.ok(result);
    }

    /**
     * GET /api/customers/type/b2b - Lấy danh sách khách hàng B2B
     */
    @GetMapping("/type/b2b")
    public ResponseEntity<Page<CustomerResponseDTO>> getB2BCustomers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<CustomerResponseDTO> result = customerService.getB2BCustomers(pageable);
        
        return ResponseEntity.ok(result);
    }

    /**
     * GET /api/customers/type/b2c - Lấy danh sách khách hàng B2C
     */
    @GetMapping("/type/b2c")
    public ResponseEntity<Page<CustomerResponseDTO>> getB2CCustomers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<CustomerResponseDTO> result = customerService.getB2CCustomers(pageable);
        
        return ResponseEntity.ok(result);
    }

    /**
     * GET /api/customers/search/email - Tìm khách hàng theo email
     */
    @GetMapping("/search/email")
    public ResponseEntity<CustomerResponseDTO> findByEmail(@RequestParam String email) {
        CustomerResponseDTO result = customerService.findByEmail(email);
        return ResponseEntity.ok(result);
    }

    /**
     * GET /api/customers/search/code - Tìm khách hàng theo mã
     */
    @GetMapping("/search/code")
    public ResponseEntity<CustomerResponseDTO> findByCustomerCode(@RequestParam String code) {
        CustomerResponseDTO result = customerService.findByCustomerCode(code);
        return ResponseEntity.ok(result);
    }

    /**
     * GET /api/customers/assigned/{userId} - Lấy danh sách khách hàng của người dùng
     */
    @GetMapping("/assigned/{userId}")
    public ResponseEntity<Page<CustomerResponseDTO>> getCustomersByAssignedUser(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<CustomerResponseDTO> result = customerService.getCustomersByAssignedUser(userId, pageable);
        
        return ResponseEntity.ok(result);
    }

    /**
     * GET /api/customers/status/{statusId} - Lấy danh sách khách hàng theo trạng thái
     */
    @GetMapping("/status/{statusId}")
    public ResponseEntity<Page<CustomerResponseDTO>> getCustomersByStatus(
            @PathVariable Long statusId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<CustomerResponseDTO> result = customerService.getCustomersByStatus(statusId, pageable);
        
        return ResponseEntity.ok(result);
    }

    /**
     * GET /api/customers/tier/{tierId} - Lấy danh sách khách hàng theo tier
     */
    @GetMapping("/tier/{tierId}")
    public ResponseEntity<Page<CustomerResponseDTO>> getCustomersByTier(
            @PathVariable Long tierId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<CustomerResponseDTO> result = customerService.getCustomersByTier(tierId, pageable);
        
        return ResponseEntity.ok(result);
    }

    /**
     * PATCH /api/customers/{id}/status - Cập nhật trạng thái khách hàng
     */
    @PatchMapping("/{id}/status")
    public ResponseEntity<Void> updateCustomerStatus(
            @PathVariable Long id,
            @RequestParam Long statusId) {
        customerService.updateCustomerStatus(id, statusId);
        return ResponseEntity.noContent().build();
    }

    /**
     * PATCH /api/customers/{id}/tier - Cập nhật tier khách hàng
     */
    @PatchMapping("/{id}/tier")
    public ResponseEntity<Void> updateCustomerTier(
            @PathVariable Long id,
            @RequestParam Long tierId) {
        customerService.updateCustomerTier(id, tierId);
        return ResponseEntity.noContent().build();
    }

    /**
     * PATCH /api/customers/{id}/assign - Gán khách hàng cho người dùng
     */
    @PatchMapping("/{id}/assign")
    public ResponseEntity<Void> assignCustomerToUser(
            @PathVariable Long id,
            @RequestParam Long userId) {
        customerService.assignCustomerToUser(id, userId);
        return ResponseEntity.noContent().build();
    }

    /**
     * GET /api/customers/count - Lấy tổng số khách hàng
     */
    @GetMapping("/count")
    public ResponseEntity<Long> getTotalCustomersCount() {
        long count = customerService.getTotalCustomersCount();
        return ResponseEntity.ok(count);
    }
}
