package org.example.crm_project.modules.leads.presentation.controller;

import lombok.RequiredArgsConstructor;
import org.example.crm_project.modules.leads.application.dto.request.CreateLeadRequest;
import org.example.crm_project.modules.leads.application.dto.request.CreateLeadActivityRequest;
import org.example.crm_project.modules.leads.application.dto.request.ConvertLeadRequest;
import org.example.crm_project.modules.leads.application.dto.request.SearchLeadRequest;
import org.example.crm_project.modules.leads.application.dto.request.UpdateLeadActivityRequest;
import org.example.crm_project.modules.leads.application.dto.request.CreateLeadMeetingRequest;
import org.example.crm_project.modules.leads.application.dto.request.UpdateLeadMeetingRequest;
import org.example.crm_project.modules.leads.application.dto.request.UpdateLeadRequest;
import org.example.crm_project.modules.leads.application.dto.response.LeadActivityResponse;
import org.example.crm_project.modules.leads.application.dto.response.LeadActivityStatisticsResponse;
import org.example.crm_project.modules.leads.application.dto.response.ConvertLeadResponse;
import org.example.crm_project.modules.leads.application.dto.response.LeadMeetingTaskResponse;
import org.example.crm_project.modules.leads.application.dto.response.LeadPageResponse;
import org.example.crm_project.modules.leads.application.dto.response.LeadResponse;
import org.example.crm_project.modules.leads.application.service.LeadActivityService;
import org.example.crm_project.modules.leads.application.service.LeadMeetingTaskService;
import org.example.crm_project.modules.leads.application.service.LeadService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    private final LeadMeetingTaskService leadMeetingTaskService;

    @PostMapping
    public ResponseEntity<LeadResponse> createLead(@RequestBody CreateLeadRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(leadService.create(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<LeadResponse> getLead(@PathVariable Long id) {
        return ResponseEntity.ok(leadService.getById(id));
    }

    @GetMapping
    public ResponseEntity<LeadPageResponse<LeadResponse>> getAllLeads(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) String sortDir
    ) {
        return ResponseEntity.ok(leadService.getAll(page, size, sortBy, sortDir));
    }

    @GetMapping("/search")
    public ResponseEntity<LeadPageResponse<LeadResponse>> searchLeads(
            @RequestParam(required = false) Integer provinceId,
            @RequestParam(required = false) Long organizationId,
            @RequestParam(required = false) String phone,
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
                .sourceId(sourceId)
                .page(page)
                .size(size)
                .sortBy(sortBy)
                .sortDir(sortDir)
                .build();

        return ResponseEntity.ok(leadService.search(request));
    }

    @PostMapping("/{id}/convert")
    public ResponseEntity<ConvertLeadResponse> convertLead(
            @PathVariable Long id,
            @RequestBody ConvertLeadRequest request
    ) {
        return ResponseEntity.ok(leadService.convert(id, request));
    }

    @PostMapping("/{id}/activities")
    public ResponseEntity<LeadActivityResponse> createLeadActivity(
            @PathVariable Long id,
            @RequestBody CreateLeadActivityRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(leadActivityService.create(id, request));
    }

    @PutMapping("/{leadId}/activities/{activityId}")
    public ResponseEntity<LeadActivityResponse> updateLeadActivity(
            @PathVariable Long leadId,
            @PathVariable Long activityId,
            @RequestBody UpdateLeadActivityRequest request
    ) {
        return ResponseEntity.ok(leadActivityService.update(leadId, activityId, request));
    }

    @GetMapping("/{id}/activities")
    public ResponseEntity<List<LeadActivityResponse>> getLeadActivities(@PathVariable Long id) {
        return ResponseEntity.ok(leadActivityService.getByLeadId(id));
    }

    @GetMapping("/{id}/activities/statistics")
    public ResponseEntity<LeadActivityStatisticsResponse> getLeadActivityStatistics(@PathVariable Long id) {
        return ResponseEntity.ok(leadActivityService.getStatistics(id));
    }

    @PostMapping("/{leadId}/meetings")
    public ResponseEntity<LeadMeetingTaskResponse> createLeadMeeting(
            @PathVariable Long leadId,
            @RequestBody CreateLeadMeetingRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(leadMeetingTaskService.create(leadId, request));
    }

    @PutMapping("/{leadId}/meetings/{meetingId}")
    public ResponseEntity<LeadMeetingTaskResponse> updateLeadMeeting(
            @PathVariable Long leadId,
            @PathVariable Long meetingId,
            @RequestBody UpdateLeadMeetingRequest request
    ) {
        return ResponseEntity.ok(leadMeetingTaskService.update(leadId, meetingId, request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LeadResponse> updateLead(@PathVariable Long id, @RequestBody UpdateLeadRequest request) {
        return ResponseEntity.ok(leadService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLead(@PathVariable Long id) {
        leadService.delete(id);
        return ResponseEntity.noContent().build();
    }
}