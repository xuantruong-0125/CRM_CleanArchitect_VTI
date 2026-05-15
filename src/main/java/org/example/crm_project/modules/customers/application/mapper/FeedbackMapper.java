package org.example.crm_project.modules.customers.application.mapper;

import org.example.crm_project.modules.customers.application.dto.request.CreateFeedbackDTO;
import org.example.crm_project.modules.customers.application.dto.response.FeedbackResponseDTO;
import org.example.crm_project.modules.customers.domain.entity.Feedback;
import org.springframework.stereotype.Component;

/**
 * Mapper: FeedbackMapper
 * DTO ↔ Domain entity conversion
 */
@Component
public class FeedbackMapper {

    public Feedback toEntity(CreateFeedbackDTO createDTO) {
        if (createDTO == null) return null;

        Feedback feedback = new Feedback();
        feedback.setCustomerId(createDTO.getCustomerId());
        feedback.setSubject(createDTO.getSubject());
        feedback.setDescription(createDTO.getDescription());
        feedback.setPriority(createDTO.getPriority());
        feedback.setStatus(createDTO.getStatus());
        feedback.setAssignedTo(createDTO.getAssignedTo());

        return feedback;
    }

    public FeedbackResponseDTO toResponseDTO(Feedback feedback) {
        if (feedback == null) return null;

        FeedbackResponseDTO dto = new FeedbackResponseDTO();
        dto.setId(feedback.getId());
        dto.setCustomerId(feedback.getCustomerId());
        dto.setSubject(feedback.getSubject());
        dto.setDescription(feedback.getDescription());
        dto.setPriority(feedback.getPriority());
        dto.setStatus(feedback.getStatus());
        dto.setAssignedTo(feedback.getAssignedTo());
        dto.setCreatedAt(feedback.getCreatedAt());
        dto.setUpdatedAt(feedback.getUpdatedAt());

        return dto;
    }

    public void updateEntityFromDTO(Feedback feedback, CreateFeedbackDTO createDTO) {
        if (feedback == null || createDTO == null) return;

        feedback.setCustomerId(createDTO.getCustomerId());
        feedback.setSubject(createDTO.getSubject());
        feedback.setDescription(createDTO.getDescription());
        feedback.setPriority(createDTO.getPriority());
        feedback.setStatus(createDTO.getStatus());
        feedback.setAssignedTo(createDTO.getAssignedTo());
    }
}
