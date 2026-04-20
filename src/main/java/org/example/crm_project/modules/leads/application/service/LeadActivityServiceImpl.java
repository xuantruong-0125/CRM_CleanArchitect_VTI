package org.example.crm_project.modules.leads.application.service;

import lombok.RequiredArgsConstructor;
import org.example.crm_project.modules.leads.application.dto.request.CreateLeadActivityRequest;
import org.example.crm_project.modules.leads.application.dto.request.UpdateLeadActivityRequest;
import org.example.crm_project.modules.leads.application.dto.response.LeadActivityResponse;
import org.example.crm_project.modules.leads.application.dto.response.LeadActivityStatisticsResponse;
import org.example.crm_project.modules.leads.application.mapper.LeadActivityMapper;
import org.example.crm_project.modules.leads.domain.constant.LeadActivityType;
import org.example.crm_project.modules.leads.domain.entity.Lead;
import org.example.crm_project.modules.leads.domain.entity.LeadActivity;
import org.example.crm_project.modules.leads.domain.exception.InvalidLeadException;
import org.example.crm_project.modules.leads.domain.exception.LeadNotFoundException;
import org.example.crm_project.modules.leads.domain.repository.LeadActivityRepository;
import org.example.crm_project.modules.leads.domain.repository.LeadReferenceRepository;
import org.example.crm_project.modules.leads.domain.repository.LeadRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class LeadActivityServiceImpl implements LeadActivityService {

    private final LeadActivityRepository leadActivityRepository;
    private final LeadRepository leadRepository;
    private final LeadReferenceRepository leadReferenceRepository;

    @Override
    public LeadActivityResponse create(Long leadId, CreateLeadActivityRequest request) {
        validateLeadId(leadId);
        getExistingLead(leadId);
        validateCreateRequest(request);

        LeadActivity leadActivity = LeadActivityMapper.toDomain(leadId, request);
        if (leadActivity.getIsImportant() == null) {
            leadActivity.setIsImportant(Boolean.FALSE);
        }

        return LeadActivityMapper.toResponse(leadActivityRepository.save(leadActivity));
    }

    @Override
    public LeadActivityResponse update(Long leadId, Long activityId, UpdateLeadActivityRequest request) {
        validateLeadId(leadId);
        validateActivityId(activityId);
        getExistingLead(leadId);
        validateUpdateRequest(request);

        LeadActivity existing = leadActivityRepository.findByIdAndLeadId(activityId, leadId);
        LeadActivity updated = merge(existing, request);
        updated.setUpdatedAt(java.time.LocalDateTime.now());

        return LeadActivityMapper.toResponse(leadActivityRepository.save(updated));
    }

    @Override
    @Transactional(readOnly = true)
    public List<LeadActivityResponse> getByLeadId(Long leadId) {
        validateLeadId(leadId);
        getExistingLead(leadId);

        return leadActivityRepository.findByLeadId(leadId)
                .stream()
                .map(LeadActivityMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public LeadActivityStatisticsResponse getStatistics(Long leadId) {
        validateLeadId(leadId);
        getExistingLead(leadId);

        return LeadActivityMapper.toStatisticsResponse(
                leadActivityRepository.getStatisticsByLeadId(leadId)
        );
    }

    private Lead getExistingLead(Long leadId) {
        return leadRepository.findById(leadId)
                .orElseThrow(() -> new LeadNotFoundException(leadId));
    }

    private void validateLeadId(Long leadId) {
        if (leadId == null || leadId <= 0) {
            throw new InvalidLeadException("leadId must be a positive number");
        }
    }

    private void validateCreateRequest(CreateLeadActivityRequest request) {
        if (request == null) {
            throw new InvalidLeadException("Create lead activity request must not be null");
        }
        if (!StringUtils.hasText(request.getActivityType())) {
            throw new InvalidLeadException("activityType is required");
        }
        if (!StringUtils.hasText(request.getSubject())) {
            throw new InvalidLeadException("subject is required");
        }
        if (request.getPerformedBy() == null || request.getPerformedBy() <= 0) {
            throw new InvalidLeadException("performedBy must be a positive number");
        }

        try {
            LeadActivityType.fromName(request.getActivityType());
        } catch (IllegalArgumentException ex) {
            throw new InvalidLeadException("activityType must be one of: CALL, MEETING, EMAIL");
        }

        if (!leadReferenceRepository.existsUserById(request.getPerformedBy())) {
            throw new InvalidLeadException("performedBy does not exist");
        }

        if (request.getCreatedBy() != null) {
            if (request.getCreatedBy() <= 0) {
                throw new InvalidLeadException("createdBy must be a positive number");
            }
            if (!leadReferenceRepository.existsUserById(request.getCreatedBy())) {
                throw new InvalidLeadException("createdBy does not exist");
            }
        }

        if (request.getEndDate() != null && request.getStartDate() != null
                && request.getEndDate().isBefore(request.getStartDate())) {
            throw new InvalidLeadException("endDate must be greater than or equal to startDate");
        }

        if (request.getCompletedAt() != null && request.getStartDate() != null
                && request.getCompletedAt().isBefore(request.getStartDate())) {
            throw new InvalidLeadException("completedAt must be greater than or equal to startDate");
        }
    }

    private void validateUpdateRequest(UpdateLeadActivityRequest request) {
        if (request == null) {
            throw new InvalidLeadException("Update lead activity request must not be null");
        }

        if (request.getPerformedBy() != null && !leadReferenceRepository.existsUserById(request.getPerformedBy())) {
            throw new InvalidLeadException("performedBy does not exist");
        }
        if (request.getUpdatedBy() != null && !leadReferenceRepository.existsUserById(request.getUpdatedBy())) {
            throw new InvalidLeadException("updatedBy does not exist");
        }

        if (request.getActivityType() != null) {
            try {
                LeadActivityType.fromName(request.getActivityType());
            } catch (IllegalArgumentException ex) {
                throw new InvalidLeadException("activityType must be one of: CALL, MEETING, EMAIL");
            }
        }

        if (request.getEndDate() != null && request.getStartDate() != null
                && request.getEndDate().isBefore(request.getStartDate())) {
            throw new InvalidLeadException("endDate must be greater than or equal to startDate");
        }

        if (request.getCompletedAt() != null && request.getStartDate() != null
                && request.getCompletedAt().isBefore(request.getStartDate())) {
            throw new InvalidLeadException("completedAt must be greater than or equal to startDate");
        }
    }

    private LeadActivity merge(LeadActivity existing, UpdateLeadActivityRequest request) {
        LeadActivity updated = LeadActivity.builder()
                .id(existing.getId())
                .leadId(existing.getLeadId())
                .activityType(existing.getActivityType())
                .subject(existing.getSubject())
                .description(existing.getDescription())
                .startDate(existing.getStartDate())
                .endDate(existing.getEndDate())
                .completedAt(existing.getCompletedAt())
                .outcome(existing.getOutcome())
                .performedBy(existing.getPerformedBy())
                .createdBy(existing.getCreatedBy())
                .updatedBy(existing.getUpdatedBy())
                .createdAt(existing.getCreatedAt())
                .updatedAt(existing.getUpdatedAt())
                .status(existing.getStatus())
                .isImportant(existing.getIsImportant())
                .build();

        if (request.getActivityType() != null) {
            updated.setActivityType(LeadActivityType.fromName(request.getActivityType()));
        }
        if (request.getSubject() != null) {
            updated.setSubject(request.getSubject().trim());
        }
        if (request.getDescription() != null) {
            updated.setDescription(request.getDescription().trim());
        }
        if (request.getStartDate() != null) {
            updated.setStartDate(request.getStartDate());
        }
        if (request.getEndDate() != null) {
            updated.setEndDate(request.getEndDate());
        }
        if (request.getCompletedAt() != null) {
            updated.setCompletedAt(request.getCompletedAt());
        }
        if (request.getOutcome() != null) {
            updated.setOutcome(request.getOutcome().trim());
        }
        if (request.getPerformedBy() != null) {
            updated.setPerformedBy(request.getPerformedBy());
        }
        if (request.getUpdatedBy() != null) {
            updated.setUpdatedBy(request.getUpdatedBy());
        }
        if (request.getStatus() != null) {
            updated.setStatus(request.getStatus());
        }
        if (request.getIsImportant() != null) {
            updated.setIsImportant(request.getIsImportant());
        }

        return updated;
    }

    private void validateActivityId(Long activityId) {
        if (activityId == null || activityId <= 0) {
            throw new InvalidLeadException("activityId must be a positive number");
        }
    }
}