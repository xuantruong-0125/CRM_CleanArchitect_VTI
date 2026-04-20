package org.example.crm_project.modules.leads.presentation.controller;

import lombok.RequiredArgsConstructor;
import org.example.crm_project.modules.leads.application.dto.response.LeadReferenceCatalogResponse;
import org.example.crm_project.modules.leads.application.service.LeadReferenceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/leads/references")
@RequiredArgsConstructor
public class LeadReferenceController {

    private final LeadReferenceService leadReferenceService;

    @GetMapping
    public ResponseEntity<LeadReferenceCatalogResponse> getReferenceCatalog() {
        return ResponseEntity.ok(leadReferenceService.getReferenceCatalog());
    }
}