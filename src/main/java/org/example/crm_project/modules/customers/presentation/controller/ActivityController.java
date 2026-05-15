package org.example.crm_project.modules.customers.presentation.controller;

import org.example.crm_project.modules.customers.application.dto.request.CreateActivityDTO;
import org.example.crm_project.modules.customers.application.dto.response.ActivityResponseDTO;
import org.example.crm_project.modules.customers.application.service.ActivityService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

/**
 * Controller: ActivityController
 * REST endpoints for Activity management
 */
@RestController
@RequestMapping("/api/activities")
@Validated
public class ActivityController {

    private final ActivityService activityService;

    public ActivityController(ActivityService activityService) {
        this.activityService = activityService;
    }

    @PostMapping
    public ResponseEntity<ActivityResponseDTO> createActivity(@Valid @RequestBody CreateActivityDTO createDTO) {
        ActivityResponseDTO created = activityService.createActivity(createDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ActivityResponseDTO> getActivityById(@PathVariable Long id) {
        ActivityResponseDTO activity = activityService.getActivityById(id);
        return ResponseEntity.ok(activity);
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<Page<ActivityResponseDTO>> getActivitiesByCustomer(
            @PathVariable Long customerId,
            Pageable pageable) {
        Page<ActivityResponseDTO> activities = activityService.getActivitiesByCustomer(customerId, pageable);
        return ResponseEntity.ok(activities);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<Page<ActivityResponseDTO>> getActivitiesByUser(
            @PathVariable Long userId,
            Pageable pageable) {
        Page<ActivityResponseDTO> activities = activityService.getActivitiesByUser(userId, pageable);
        return ResponseEntity.ok(activities);
    }

    @GetMapping("/type/{activityType}")
    public ResponseEntity<Page<ActivityResponseDTO>> getActivitiesByType(
            @PathVariable String activityType,
            Pageable pageable) {
        Page<ActivityResponseDTO> activities = activityService.getActivitiesByType(activityType, pageable);
        return ResponseEntity.ok(activities);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ActivityResponseDTO> updateActivity(
            @PathVariable Long id,
            @Valid @RequestBody CreateActivityDTO createDTO) {
        ActivityResponseDTO updated = activityService.updateActivity(id, createDTO);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteActivity(@PathVariable Long id) {
        activityService.deleteActivity(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/count")
    public ResponseEntity<Long> countActivities() {
        long count = activityService.countActivities();
        return ResponseEntity.ok(count);
    }
}
