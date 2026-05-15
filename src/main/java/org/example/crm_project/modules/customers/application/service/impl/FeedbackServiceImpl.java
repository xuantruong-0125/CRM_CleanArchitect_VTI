package org.example.crm_project.modules.customers.application.service.impl;

import org.example.crm_project.modules.customers.application.dto.request.CreateFeedbackDTO;
import org.example.crm_project.modules.customers.application.dto.response.FeedbackResponseDTO;
import org.example.crm_project.modules.customers.application.mapper.FeedbackMapper;
import org.example.crm_project.modules.customers.application.service.FeedbackService;
import org.example.crm_project.modules.customers.domain.entity.Feedback;
import org.example.crm_project.modules.customers.domain.repository.FeedbackRepository;
import org.example.crm_project.modules.customers.domain.exception.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation: FeedbackServiceImpl
 * Business logic for Feedback management
 */
@Service
@Transactional
public class FeedbackServiceImpl implements FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final FeedbackMapper feedbackMapper;

    public FeedbackServiceImpl(FeedbackRepository feedbackRepository, FeedbackMapper feedbackMapper) {
        this.feedbackRepository = feedbackRepository;
        this.feedbackMapper = feedbackMapper;
    }

    @Override
    public FeedbackResponseDTO createFeedback(CreateFeedbackDTO createDTO) {
        Feedback feedback = feedbackMapper.toEntity(createDTO);
        Feedback saved = feedbackRepository.save(feedback);
        return feedbackMapper.toResponseDTO(saved);
    }

    @Override
    public FeedbackResponseDTO getFeedbackById(Long id) {
        Feedback feedback = feedbackRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Phản hồi không tìm thấy: " + id));
        return feedbackMapper.toResponseDTO(feedback);
    }

    @Override
    public Page<FeedbackResponseDTO> getFeedbacksByCustomer(Long customerId, Pageable pageable) {
        Page<Feedback> feedbacks = feedbackRepository.findByCustomerId(customerId, pageable);
        return feedbacks.map(feedbackMapper::toResponseDTO);
    }

    @Override
    public Page<FeedbackResponseDTO> getFeedbacksByStatus(String status, Pageable pageable) {
        Page<Feedback> feedbacks = feedbackRepository.findByStatus(status, pageable);
        return feedbacks.map(feedbackMapper::toResponseDTO);
    }

    @Override
    public Page<FeedbackResponseDTO> getFeedbacksByPriority(String priority, Pageable pageable) {
        Page<Feedback> feedbacks = feedbackRepository.findByPriority(priority, pageable);
        return feedbacks.map(feedbackMapper::toResponseDTO);
    }

    @Override
    public FeedbackResponseDTO updateFeedback(Long id, CreateFeedbackDTO createDTO) {
        Feedback feedback = feedbackRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Phản hồi không tìm thấy: " + id));
        feedbackMapper.updateEntityFromDTO(feedback, createDTO);
        Feedback updated = feedbackRepository.save(feedback);
        return feedbackMapper.toResponseDTO(updated);
    }

    @Override
    public void deleteFeedback(Long id) {
        if (!feedbackRepository.existsById(id)) {
            throw new EntityNotFoundException("Phản hồi không tìm thấy: " + id);
        }
        feedbackRepository.delete(id);
    }

    @Override
    @Transactional(readOnly = true)
    public long countFeedbacks() {
        return feedbackRepository.count();
    }
}
