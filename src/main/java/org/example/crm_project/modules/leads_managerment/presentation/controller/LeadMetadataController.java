package org.example.crm_project.modules.leads_managerment.presentation.controller;

import lombok.RequiredArgsConstructor;
import org.example.crm_project.modules.leads_managerment.application.dto.response.LeadPageResponse;
import org.example.crm_project.modules.leads_managerment.application.dto.response.LeadReferenceOptionResponse;
import org.example.crm_project.modules.leads_managerment.application.service.LeadReferenceService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/leads/metadata")
@RequiredArgsConstructor
public class LeadMetadataController {

    private final LeadReferenceService leadReferenceService;

    @GetMapping("/assignees")
    @PreAuthorize("hasAuthority('LEAD_VIEW')")
    public ResponseEntity<LeadPageResponse<LeadReferenceOptionResponse>> searchAssignees(
            @RequestParam(required = false) String q,
            @RequestParam(required = false) Long organizationId,
            @RequestParam(required = false) Long roleId,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) String sortDir
    ) {
        return ResponseEntity.ok(leadReferenceService.searchAssignees(
                q,
                organizationId,
                roleId,
                status,
                page,
                size,
                sortBy,
                sortDir
        ));
    }

    @GetMapping("/products")
    @PreAuthorize("hasAuthority('LEAD_VIEW')")
    public ResponseEntity<LeadPageResponse<LeadReferenceOptionResponse>> searchProducts(
            @RequestParam(required = false) String q,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) Boolean isActive,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) String sortDir
    ) {
        return ResponseEntity.ok(leadReferenceService.searchProducts(
                q,
                type,
                categoryId,
                isActive,
                page,
                size,
                sortBy,
                sortDir
        ));
    }

    @GetMapping("/organizations")
    @PreAuthorize("hasAuthority('LEAD_VIEW')")
    public ResponseEntity<LeadPageResponse<LeadReferenceOptionResponse>> searchOrganizations(
            @RequestParam(required = false) String q,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) String sortDir
    ) {
        return ResponseEntity.ok(leadReferenceService.searchOrganizations(q, page, size, sortBy, sortDir));
    }
}
