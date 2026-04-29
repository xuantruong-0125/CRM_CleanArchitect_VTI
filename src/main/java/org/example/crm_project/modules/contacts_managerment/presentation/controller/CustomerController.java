package org.example.crm_project.modules.contacts_managerment.presentation.controller;

import org.example.crm_project.modules.contacts_managerment.application.interfaces.CustomerService;
import org.example.crm_project.modules.contacts_managerment.domain.entity.Customer;
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
    public ResponseEntity<ApiResponse<Customer>> create(@RequestBody Customer customer) {
        return ResponseEntity.ok(ApiResponse.success(customerService.create(customer), "Customer created successfully"));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Customer>>> getAll() {
        return ResponseEntity.ok(ApiResponse.success(customerService.getAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Customer>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(customerService.getById(id)));
    }
}
