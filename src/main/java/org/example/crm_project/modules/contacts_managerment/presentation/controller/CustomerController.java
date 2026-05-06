package org.example.crm_project.modules.contacts_managerment.presentation.controller;

import org.example.crm_project.modules.contacts_managerment.application.dto.request.CreateCustomerRequest;
import org.example.crm_project.modules.contacts_managerment.application.dto.request.UpdateCustomerRequest;
import org.example.crm_project.modules.contacts_managerment.application.dto.response.CustomerResponse;
import org.example.crm_project.modules.contacts_managerment.application.dto.response.PageResponse;
import org.example.crm_project.modules.contacts_managerment.application.interfaces.CustomerService;
import org.example.crm_project.modules.contacts_managerment.domain.entity.CustomerType;
import org.example.crm_project.modules.contacts_managerment.presentation.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CustomerResponse>> create(@RequestBody CreateCustomerRequest request) {
        return ResponseEntity.ok(ApiResponse.success(customerService.create(request), "Customer created successfully"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CustomerResponse>> update(@PathVariable Long id, @RequestBody UpdateCustomerRequest request) {
        return ResponseEntity.ok(ApiResponse.success(customerService.update(id, request), "Customer updated successfully"));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<CustomerResponse>>> search(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) CustomerType type,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(ApiResponse.success(customerService.search(keyword, type, page, size)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CustomerResponse>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(customerService.getById(id)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        customerService.delete(id);
        return ResponseEntity.ok(ApiResponse.success(null, "Customer deleted successfully"));
    }

    @DeleteMapping("/bulk")
    public ResponseEntity<ApiResponse<Void>> bulkDelete(@RequestBody List<Long> ids) {
        customerService.deleteByIds(ids);
        return ResponseEntity.ok(ApiResponse.success(null, "Customers deleted successfully"));
    }
}
