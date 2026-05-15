package org.example.crm_project.modules.customers.presentation.controller;

import org.example.crm_project.modules.customers.application.dto.request.CreateCustomerAddressDTO;
import org.example.crm_project.modules.customers.application.dto.response.CustomerAddressResponseDTO;
import org.example.crm_project.modules.customers.application.service.CustomerAddressService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

/**
 * Controller: CustomerAddressController
 * REST endpoints for CustomerAddress management
 */
@RestController
@RequestMapping("/api/customer-addresses")
@Validated
public class CustomerAddressController {

    private final CustomerAddressService customerAddressService;

    public CustomerAddressController(CustomerAddressService customerAddressService) {
        this.customerAddressService = customerAddressService;
    }

    @PostMapping
    public ResponseEntity<CustomerAddressResponseDTO> createCustomerAddress(@Valid @RequestBody CreateCustomerAddressDTO createDTO) {
        CustomerAddressResponseDTO created = customerAddressService.createCustomerAddress(createDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerAddressResponseDTO> getCustomerAddressById(@PathVariable Long id) {
        CustomerAddressResponseDTO address = customerAddressService.getCustomerAddressById(id);
        return ResponseEntity.ok(address);
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<CustomerAddressResponseDTO>> getAddressesByCustomer(
            @PathVariable Long customerId) {
        List<CustomerAddressResponseDTO> addresses = customerAddressService.getAddressesByCustomer(customerId);
        return ResponseEntity.ok(addresses);
    }

    @GetMapping("/customer-paginated/{customerId}")
    public ResponseEntity<Page<CustomerAddressResponseDTO>> getAddressesByCustomerPaginated(
            @PathVariable Long customerId,
            Pageable pageable) {
        Page<CustomerAddressResponseDTO> addresses = customerAddressService.getAddressesByCustomerPaginated(customerId, pageable);
        return ResponseEntity.ok(addresses);
    }

    @GetMapping("/primary/{customerId}")
    public ResponseEntity<?> getPrimaryAddress(@PathVariable Long customerId) {
        Optional<CustomerAddressResponseDTO> address = customerAddressService.getPrimaryAddress(customerId);
        return address.isPresent() ? ResponseEntity.ok(address.get()) : ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerAddressResponseDTO> updateCustomerAddress(
            @PathVariable Long id,
            @Valid @RequestBody CreateCustomerAddressDTO createDTO) {
        CustomerAddressResponseDTO updated = customerAddressService.updateCustomerAddress(id, createDTO);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomerAddress(@PathVariable Long id) {
        customerAddressService.deleteCustomerAddress(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/customer/{customerId}")
    public ResponseEntity<Void> deleteAddressesByCustomer(@PathVariable Long customerId) {
        customerAddressService.deleteAddressesByCustomer(customerId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/count")
    public ResponseEntity<Long> countAddresses() {
        long count = customerAddressService.countAddresses();
        return ResponseEntity.ok(count);
    }
}
