package org.example.crm_project.modules.customers.application.service.impl;

import org.example.crm_project.modules.customers.application.dto.request.CreateActivityDTO;
import org.example.crm_project.modules.customers.application.dto.response.ActivityResponseDTO;
import org.example.crm_project.modules.customers.application.mapper.ActivityMapper;
import org.example.crm_project.modules.customers.application.service.ActivityService;
import org.example.crm_project.modules.customers.domain.entity.Activity;
import org.example.crm_project.modules.customers.domain.repository.ActivityRepository;
import org.example.crm_project.modules.customers.domain.exception.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation: ActivityServiceImpl
 * Business logic for Activity management
 */
@Service("customerActivityService")
@Transactional
public class ActivityServiceImpl implements ActivityService {

    private final ActivityRepository activityRepository;
    private final ActivityMapper activityMapper;

    public ActivityServiceImpl(ActivityRepository activityRepository, ActivityMapper activityMapper) {
        this.activityRepository = activityRepository;
        this.activityMapper = activityMapper;
    }

    @Override
    public ActivityResponseDTO createActivity(CreateActivityDTO createDTO) {
        Activity activity = activityMapper.toEntity(createDTO);
        Activity saved = activityRepository.save(activity);
        return activityMapper.toResponseDTO(saved);
    }

    @Override
    public ActivityResponseDTO getActivityById(Long id) {
        Activity activity = activityRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Hoạt động không tìm thấy: " + id));
        return activityMapper.toResponseDTO(activity);
    }

    @Override
    public Page<ActivityResponseDTO> getActivitiesByCustomer(Long customerId, Pageable pageable) {
        Page<Activity> activities = activityRepository.findByRelatedToTypeAndRelatedToId("CUSTOMER", customerId, pageable);
        return activities.map(activityMapper::toResponseDTO);
    }

    @Override
    public Page<ActivityResponseDTO> getActivitiesByUser(Long userId, Pageable pageable) {
        Page<Activity> activities = activityRepository.findByPerformedBy(userId, pageable);
        return activities.map(activityMapper::toResponseDTO);
    }

    @Override
    public Page<ActivityResponseDTO> getActivitiesByType(String activityType, Pageable pageable) {
        Page<Activity> activities = activityRepository.findByActivityType(activityType, pageable);
        return activities.map(activityMapper::toResponseDTO);
    }

    @Override
    public ActivityResponseDTO updateActivity(Long id, CreateActivityDTO createDTO) {
        Activity activity = activityRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Hoạt động không tìm thấy: " + id));
        activityMapper.updateEntityFromDTO(activity, createDTO);
        Activity updated = activityRepository.save(activity);
        return activityMapper.toResponseDTO(updated);
    }

    @Override
    public void deleteActivity(Long id) {
        if (!activityRepository.existsById(id)) {
            throw new EntityNotFoundException("Hoạt động không tìm thấy: " + id);
        }
        activityRepository.delete(id);
    }

    @Override
    @Transactional(readOnly = true)
    public long countActivities() {
        return activityRepository.count();
    }
}
