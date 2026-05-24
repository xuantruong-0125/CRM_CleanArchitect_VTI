package org.example.crm_project.modules.leads_managerment.presentation.controller;

import lombok.RequiredArgsConstructor;
import org.example.crm_project.modules.leads_managerment.application.dto.request.CreateLeadRequest;
import org.example.crm_project.modules.leads_managerment.application.dto.request.ConvertLeadRequest;
import org.example.crm_project.modules.leads_managerment.application.dto.request.SearchLeadRequest;
import org.example.crm_project.modules.leads_managerment.application.dto.request.UpdateLeadRequest;
import org.example.crm_project.modules.leads_managerment.application.dto.response.*;
import org.example.crm_project.modules.leads_managerment.application.service.LeadActivityService;
import org.example.crm_project.modules.leads_managerment.application.service.LeadService;
import org.example.crm_project.modules.leads_managerment.application.service.LeadTaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/leads")
@RequiredArgsConstructor
public class LeadController {

    private final LeadService leadService;
    private final LeadActivityService leadActivityService;
    private final LeadTaskService leadTaskService;

    @PostMapping
    @PreAuthorize("hasAuthority('LEAD_CREATE')")
    public ResponseEntity<LeadResponse> createLead(@RequestBody CreateLeadRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(leadService.create(request));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('LEAD_VIEW')")
    public ResponseEntity<LeadResponse> getLead(@PathVariable Long id) {
        return ResponseEntity.ok(leadService.getById(id));
    }

    @GetMapping
    @PreAuthorize("hasAuthority('LEAD_VIEW')")
    public ResponseEntity<LeadPageResponse<LeadResponse>> getAllLeads(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) String sortDir
    ) {
        return ResponseEntity.ok(leadService.getAll(page, size, sortBy, sortDir));
    }

    @GetMapping("/search")
    @PreAuthorize("hasAuthority('LEAD_VIEW')")
    public ResponseEntity<LeadPageResponse<LeadResponse>> searchLeads(
            @RequestParam(required = false) Integer provinceId,
            @RequestParam(required = false) Long organizationId,
            @RequestParam(required = false) String phone,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) Long statusId,
            @RequestParam(required = false) Long sourceId,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) String sortDir
    ) {
        SearchLeadRequest request = SearchLeadRequest.builder()
                .provinceId(provinceId)
                .organizationId(organizationId)
                .phone(phone)
                .email(email)
                .statusId(statusId)
                .sourceId(sourceId)
                .page(page)
                .size(size)
                .sortBy(sortBy)
                .sortDir(sortDir)
                .build();

        return ResponseEntity.ok(leadService.search(request));
    }

    @PostMapping("/{id}/convert")
    @PreAuthorize("hasAuthority('LEAD_VIEW')")
    public ResponseEntity<ConvertLeadResponse> convertLead(
            @PathVariable Long id,
            @RequestBody ConvertLeadRequest request
    ) {
        return ResponseEntity.ok(leadService.convert(id, request));
    }

    @GetMapping("/{id}/activities/statistics")
    @PreAuthorize("hasAuthority('LEAD_VIEW')")
    public ResponseEntity<LeadActivityStatisticsResponse> getLeadActivityStatistics(@PathVariable Long id) {
        return ResponseEntity.ok(leadActivityService.getStatistics(id));
    }

    @GetMapping("/{id}/tasks")
    @PreAuthorize("hasAuthority('LEAD_VIEW')")
    public ResponseEntity<List<LeadTaskResponse>> getLeadTasks(@PathVariable Long id) {
        return ResponseEntity.ok(leadTaskService.getTasksByLeadId(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('LEAD_UPDATE')")
    public ResponseEntity<LeadResponse> updateLead(@PathVariable Long id, @RequestBody UpdateLeadRequest request) {
        return ResponseEntity.ok(leadService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('LEAD_DELETE')")
    public ResponseEntity<Void> deleteLead(@PathVariable Long id) {
        leadService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
