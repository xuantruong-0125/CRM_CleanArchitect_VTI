package org.example.crm_project.modules.customers.presentation.controller;

import org.example.crm_project.modules.customers.application.dto.request.CreateContractDTO;
import org.example.crm_project.modules.customers.application.dto.response.ContractResponseDTO;
import org.example.crm_project.modules.customers.application.service.ContractService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.Optional;

/**
 * Controller: ContractController
 * REST endpoints for Contract management
 */
@RestController
@RequestMapping("/api/contracts")
@Validated
public class ContractController {

    private final ContractService contractService;

    public ContractController(ContractService contractService) {
        this.contractService = contractService;
    }

    @PostMapping
    public ResponseEntity<ContractResponseDTO> createContract(@Valid @RequestBody CreateContractDTO createDTO) {
        ContractResponseDTO created = contractService.createContract(createDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContractResponseDTO> getContractById(@PathVariable Long id) {
        ContractResponseDTO contract = contractService.getContractById(id);
        return ResponseEntity.ok(contract);
    }

    @GetMapping("/code/{contractCode}")
    public ResponseEntity<?> getContractByCode(@PathVariable String contractCode) {
        Optional<ContractResponseDTO> contract = contractService.getContractByCode(contractCode);
        return contract.isPresent() ? ResponseEntity.ok(contract.get()) : ResponseEntity.notFound().build();
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<Page<ContractResponseDTO>> getContractsByCustomer(
            @PathVariable Long customerId,
            Pageable pageable) {
        Page<ContractResponseDTO> contracts = contractService.getContractsByCustomer(customerId, pageable);
        return ResponseEntity.ok(contracts);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<Page<ContractResponseDTO>> getContractsByStatus(
            @PathVariable String status,
            Pageable pageable) {
        Page<ContractResponseDTO> contracts = contractService.getContractsByStatus(status, pageable);
        return ResponseEntity.ok(contracts);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ContractResponseDTO> updateContract(
            @PathVariable Long id,
            @Valid @RequestBody CreateContractDTO createDTO) {
        ContractResponseDTO updated = contractService.updateContract(id, createDTO);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContract(@PathVariable Long id) {
        contractService.deleteContract(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/count")
    public ResponseEntity<Long> countContracts() {
        long count = contractService.countContracts();
        return ResponseEntity.ok(count);
    }
}
