package org.example.crm_project.modules.customers.presentation.controller;

import org.example.crm_project.modules.customers.application.dto.response.ContactResponseDTO;
import org.example.crm_project.modules.customers.application.dto.request.CreateContactDTO;
import org.example.crm_project.modules.customers.application.service.ContactService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

/**
 * Controller: ContactController
 * REST endpoints cho quản lý người liên hệ
 */
@RestController
@RequestMapping("/api/contacts")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ContactController {

    private final ContactService contactService;

    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    /**
     * POST /api/contacts/customer/{customerId} - Tạo người liên hệ mới
     */
    @PostMapping("/customer/{customerId}")
    public ResponseEntity<ContactResponseDTO> createContact(
            @PathVariable Long customerId,
            @Valid @RequestBody CreateContactDTO createDTO) {
        ContactResponseDTO result = contactService.createContact(customerId, createDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    /**
     * GET /api/contacts/{id} - Lấy thông tin người liên hệ
     */
    @GetMapping("/{id}")
    public ResponseEntity<ContactResponseDTO> getContactById(@PathVariable Long id) {
        ContactResponseDTO result = contactService.getContactById(id);
        return ResponseEntity.ok(result);
    }

    /**
     * PUT /api/contacts/{id} - Cập nhật người liên hệ
     */
    @PutMapping("/{id}")
    public ResponseEntity<ContactResponseDTO> updateContact(
            @PathVariable Long id,
            @Valid @RequestBody CreateContactDTO updateDTO) {
        ContactResponseDTO result = contactService.updateContact(id, updateDTO);
        return ResponseEntity.ok(result);
    }

    /**
     * DELETE /api/contacts/{id} - Xóa người liên hệ
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContact(@PathVariable Long id) {
        contactService.deleteContact(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * GET /api/contacts/customer/{customerId} - Lấy danh sách người liên hệ của khách hàng
     */
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<ContactResponseDTO>> getContactsByCustomer(@PathVariable Long customerId) {
        List<ContactResponseDTO> result = contactService.getContactsByCustomer(customerId);
        return ResponseEntity.ok(result);
    }

    /**
     * GET /api/contacts/customer/{customerId}/page - Lấy danh sách người liên hệ phân trang
     */
    @GetMapping("/customer/{customerId}/page")
    public ResponseEntity<Page<ContactResponseDTO>> getContactsByCustomerPaginated(
            @PathVariable Long customerId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<ContactResponseDTO> result = contactService.getContactsByCustomerPaginated(customerId, pageable);
        
        return ResponseEntity.ok(result);
    }

    /**
     * GET /api/contacts/customer/{customerId}/primary - Lấy người liên hệ chính
     */
    @GetMapping("/customer/{customerId}/primary")
    public ResponseEntity<ContactResponseDTO> getPrimaryContact(@PathVariable Long customerId) {
        ContactResponseDTO result = contactService.getPrimaryContact(customerId);
        return ResponseEntity.ok(result);
    }

    /**
     * PATCH /api/contacts/{id}/set-primary - Đặt người liên hệ làm chính
     */
    @PatchMapping("/{id}/set-primary")
    public ResponseEntity<Void> setPrimaryContact(@PathVariable Long id) {
        contactService.setPrimaryContact(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * DELETE /api/contacts/customer/{customerId} - Xóa tất cả người liên hệ của khách hàng
     */
    @DeleteMapping("/customer/{customerId}")
    public ResponseEntity<Void> deleteAllContactsByCustomer(@PathVariable Long customerId) {
        contactService.deleteAllContactsByCustomer(customerId);
        return ResponseEntity.noContent().build();
    }

    /**
     * GET /api/contacts/customer/{customerId}/count - Lấy tổng số người liên hệ
     */
    @GetMapping("/customer/{customerId}/count")
    public ResponseEntity<Long> getTotalContactsCount(@PathVariable Long customerId) {
        long count = contactService.getTotalContactsCount(customerId);
        return ResponseEntity.ok(count);
    }
}
