package org.example.crm_project.modules.leads.domain.repository;

import org.example.crm_project.modules.leads.domain.entity.LeadMeetingTask;

public interface LeadMeetingTaskRepository {

    LeadMeetingTask save(LeadMeetingTask meetingTask);

    LeadMeetingTask findByIdAndLeadId(Long meetingTaskId, Long leadId);
}