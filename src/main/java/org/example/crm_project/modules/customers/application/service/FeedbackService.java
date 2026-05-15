package org.example.crm_project.modules.customers.application.service;

import org.example.crm_project.modules.customers.application.dto.request.CreateFeedbackDTO;
import org.example.crm_project.modules.customers.application.dto.response.FeedbackResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FeedbackService {

    FeedbackResponseDTO createFeedback(CreateFeedbackDTO createDTO);

    FeedbackResponseDTO getFeedbackById(Long id);

    Page<FeedbackResponseDTO> getFeedbacksByCustomer(Long customerId, Pageable pageable);

    Page<FeedbackResponseDTO> getFeedbacksByStatus(String status, Pageable pageable);

    Page<FeedbackResponseDTO> getFeedbacksByPriority(String priority, Pageable pageable);

    FeedbackResponseDTO updateFeedback(Long id, CreateFeedbackDTO createDTO);

    void deleteFeedback(Long id);

    long countFeedbacks();
}
