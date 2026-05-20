package org.example.crm_project.modules.customers.application.service;

import org.example.crm_project.modules.customers.application.dto.request.CreateActivityDTO;
import org.example.crm_project.modules.customers.application.dto.response.ActivityResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface: ActivityService
 */
public interface ActivityService {

    ActivityResponseDTO createActivity(CreateActivityDTO createDTO);

    ActivityResponseDTO getActivityById(Long id);

    Page<ActivityResponseDTO> getActivitiesByCustomer(Long customerId, Pageable pageable);

    Page<ActivityResponseDTO> getActivitiesByUser(Long userId, Pageable pageable);

    Page<ActivityResponseDTO> getActivitiesByType(String activityType, Pageable pageable);

    ActivityResponseDTO updateActivity(Long id, CreateActivityDTO createDTO);

    void deleteActivity(Long id);

    long countActivities();
}
