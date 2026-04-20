package org.example.crm_project.modules.leads.application.service;

import org.example.crm_project.modules.leads.application.dto.request.CreateLeadMeetingRequest;
import org.example.crm_project.modules.leads.application.dto.request.UpdateLeadMeetingRequest;
import org.example.crm_project.modules.leads.application.dto.response.LeadMeetingTaskResponse;

public interface LeadMeetingTaskService {

    LeadMeetingTaskResponse create(Long leadId, CreateLeadMeetingRequest request);

    LeadMeetingTaskResponse update(Long leadId, Long meetingTaskId, UpdateLeadMeetingRequest request);
}