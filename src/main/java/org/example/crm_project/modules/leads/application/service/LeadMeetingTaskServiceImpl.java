package org.example.crm_project.modules.leads.application.service;

import lombok.RequiredArgsConstructor;
import org.example.crm_project.modules.leads.application.dto.request.CreateLeadMeetingRequest;
import org.example.crm_project.modules.leads.application.dto.request.UpdateLeadMeetingRequest;
import org.example.crm_project.modules.leads.application.dto.response.LeadMeetingTaskResponse;
import org.example.crm_project.modules.leads.application.mapper.LeadMeetingTaskMapper;
import org.example.crm_project.modules.leads.domain.entity.Lead;
import org.example.crm_project.modules.leads.domain.entity.LeadMeetingTask;
import org.example.crm_project.modules.leads.domain.exception.InvalidLeadException;
import org.example.crm_project.modules.leads.domain.exception.LeadNotFoundException;
import org.example.crm_project.modules.leads.domain.repository.LeadMeetingTaskRepository;
import org.example.crm_project.modules.leads.domain.repository.LeadReferenceRepository;
import org.example.crm_project.modules.leads.domain.repository.LeadRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class LeadMeetingTaskServiceImpl implements LeadMeetingTaskService {

    private final LeadMeetingTaskRepository leadMeetingTaskRepository;
    private final LeadRepository leadRepository;
    private final LeadReferenceRepository leadReferenceRepository;

    @Override
    public LeadMeetingTaskResponse create(Long leadId, CreateLeadMeetingRequest request) {
        validateLeadId(leadId);
        getExistingLead(leadId);
        validateCreateRequest(request);

        LeadMeetingTask meetingTask = LeadMeetingTaskMapper.toDomain(leadId, request);
        meetingTask.setCreatedAt(LocalDateTime.now());
        meetingTask.setUpdatedAt(LocalDateTime.now());
        meetingTask.setProgressPercent(request.getProgressPercent() == null ? 0 : request.getProgressPercent());

        return LeadMeetingTaskMapper.toResponse(leadMeetingTaskRepository.save(meetingTask));
    }

    @Override
    public LeadMeetingTaskResponse update(Long leadId, Long meetingTaskId, UpdateLeadMeetingRequest request) {
        validateLeadId(leadId);
        validateMeetingTaskId(meetingTaskId);
        getExistingLead(leadId);
        validateUpdateRequest(request);

        LeadMeetingTask existing = leadMeetingTaskRepository.findByIdAndLeadId(meetingTaskId, leadId);
        LeadMeetingTask updated = LeadMeetingTaskMapper.merge(existing, request);
        updated.setUpdatedAt(LocalDateTime.now());

        return LeadMeetingTaskMapper.toResponse(leadMeetingTaskRepository.save(updated));
    }

    private Lead getExistingLead(Long leadId) {
        return leadRepository.findById(leadId)
                .orElseThrow(() -> new LeadNotFoundException(leadId));
    }

    private void validateCreateRequest(CreateLeadMeetingRequest request) {
        if (request == null) {
            throw new InvalidLeadException("Create lead meeting request must not be null");
        }
        if (!StringUtils.hasText(request.getSubject())) {
            throw new InvalidLeadException("subject is required");
        }
        if (request.getDueDate() == null) {
            throw new InvalidLeadException("dueDate is required");
        }
        if (request.getAssignedTo() == null || request.getAssignedTo() <= 0) {
            throw new InvalidLeadException("assignedTo must be a positive number");
        }
        if (request.getAssignedBy() == null || request.getAssignedBy() <= 0) {
            throw new InvalidLeadException("assignedBy must be a positive number");
        }

        validateUserExists(request.getAssignedTo(), "assignedTo");
        validateUserExists(request.getAssignedBy(), "assignedBy");
        if (request.getCreatedBy() != null) {
            validateUserExists(request.getCreatedBy(), "createdBy");
        }
        validateOptionalPriority(request.getPriority());
        validateOptionalStatus(request.getStatus());
        validateDueDateRange(request.getStartDate(), request.getDueDate(), request.getCompletedAt());
        validateProgressPercent(request.getProgressPercent());
    }

    private void validateUpdateRequest(UpdateLeadMeetingRequest request) {
        if (request == null) {
            throw new InvalidLeadException("Update lead meeting request must not be null");
        }
        if (request.getAssignedTo() != null) {
            validateUserExists(request.getAssignedTo(), "assignedTo");
        }
        if (request.getAssignedBy() != null) {
            validateUserExists(request.getAssignedBy(), "assignedBy");
        }
        if (request.getUpdatedBy() != null) {
            validateUserExists(request.getUpdatedBy(), "updatedBy");
        }
        validateOptionalPriority(request.getPriority());
        validateOptionalStatus(request.getStatus());
        validateDueDateRange(request.getStartDate(), request.getDueDate(), request.getCompletedAt());
        validateProgressPercent(request.getProgressPercent());
    }

    private void validateMeetingTaskId(Long meetingTaskId) {
        if (meetingTaskId == null || meetingTaskId <= 0) {
            throw new InvalidLeadException("meetingTaskId must be a positive number");
        }
    }

    private void validateLeadId(Long leadId) {
        if (leadId == null || leadId <= 0) {
            throw new InvalidLeadException("leadId must be a positive number");
        }
    }

    private void validateUserExists(Long userId, String fieldName) {
        if (!leadReferenceRepository.existsUserById(userId)) {
            throw new InvalidLeadException(fieldName + " does not exist");
        }
    }

    private void validateOptionalPriority(String priority) {
        if (priority == null) {
            return;
        }

        if (!priority.equalsIgnoreCase("LOW")
                && !priority.equalsIgnoreCase("NORMAL")
                && !priority.equalsIgnoreCase("HIGH")
                && !priority.equalsIgnoreCase("URGENT")) {
            throw new InvalidLeadException("priority must be LOW, NORMAL, HIGH or URGENT");
        }
    }

    private void validateOptionalStatus(String status) {
        if (status == null) {
            return;
        }

        if (!status.equalsIgnoreCase("NOT_STARTED")
                && !status.equalsIgnoreCase("IN_PROGRESS")
                && !status.equalsIgnoreCase("WAITING")
                && !status.equalsIgnoreCase("COMPLETED")
                && !status.equalsIgnoreCase("DEFERRED")) {
            throw new InvalidLeadException("status must be NOT_STARTED, IN_PROGRESS, WAITING, COMPLETED or DEFERRED");
        }
    }

    private void validateDueDateRange(LocalDateTime startDate, LocalDateTime dueDate, LocalDateTime completedAt) {
        if (startDate != null && dueDate != null && dueDate.isBefore(startDate)) {
            throw new InvalidLeadException("dueDate must be greater than or equal to startDate");
        }
        if (startDate != null && completedAt != null && completedAt.isBefore(startDate)) {
            throw new InvalidLeadException("completedAt must be greater than or equal to startDate");
        }
    }

    private void validateProgressPercent(Integer progressPercent) {
        if (progressPercent == null) {
            return;
        }

        if (progressPercent < 0 || progressPercent > 100) {
            throw new InvalidLeadException("progressPercent must be between 0 and 100");
        }
    }
}