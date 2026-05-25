package org.example.crm_project.modules.leads_managerment.presentation.controller;

import lombok.RequiredArgsConstructor;
import org.example.crm_project.modules.leads_managerment.application.dto.response.LeadReferenceCatalogResponse;
import org.example.crm_project.modules.leads_managerment.application.service.LeadReferenceService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/leads/references")
@RequiredArgsConstructor
public class LeadReferenceController {

    private final LeadReferenceService leadReferenceService;

    @GetMapping
    @PreAuthorize("hasAuthority('LEAD_VIEW')")
    public ResponseEntity<LeadReferenceCatalogResponse> getReferenceCatalog() {
        return ResponseEntity.ok(leadReferenceService.getReferenceCatalog());
    }
}
