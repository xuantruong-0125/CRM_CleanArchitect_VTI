package org.example.crm_project.modules.contacts_managerment.presentation.controller;

import org.example.crm_project.modules.contacts_managerment.application.dto.request.CreateContactRequest;
import org.example.crm_project.modules.contacts_managerment.application.dto.request.UpdateContactRequest;
import org.example.crm_project.modules.contacts_managerment.application.dto.response.ContactResponse;
import org.example.crm_project.modules.contacts_managerment.application.dto.response.PageResponse;
import org.example.crm_project.modules.contacts_managerment.application.interfaces.ContactService;
import org.example.crm_project.modules.contacts_managerment.presentation.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/contacts")
public class ContactController {

    private final ContactService contactService;

    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ContactResponse>> createContact(@RequestBody CreateContactRequest request) {
        ContactResponse response = contactService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(response, "Contact created successfully"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ContactResponse>> updateContact(
            @PathVariable Long id,
            @RequestBody UpdateContactRequest request) {
        ContactResponse response = contactService.update(id, request);
        return ResponseEntity.ok(ApiResponse.success(response, "Contact updated successfully"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteContact(@PathVariable Long id) {
        contactService.delete(id);
        return ResponseEntity.ok(ApiResponse.success(null, "Contact deleted successfully"));
    }

    @DeleteMapping("/bulk")
    public ResponseEntity<ApiResponse<Void>> deleteContactsBulk(@RequestBody List<Long> ids) {
        contactService.deleteByIds(ids);
        return ResponseEntity.ok(ApiResponse.success(null, "Contacts deleted successfully"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ContactResponse>> getContactById(@PathVariable Long id) {
        ContactResponse response = contactService.getById(id);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<ContactResponse>>> searchContacts(
            @RequestParam(required = false, defaultValue = "") String keyword,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        PageResponse<ContactResponse> response = contactService.search(keyword, page, size);
        return ResponseEntity.ok(ApiResponse.success(response));
    }
}
