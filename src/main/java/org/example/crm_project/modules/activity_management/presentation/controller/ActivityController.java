package org.example.crm_project.modules.activity_management.presentation.controller;

import lombok.RequiredArgsConstructor;

import org.example.crm_project.modules.activity_management.application.dto.request.ActivitySearchCriteria;
import org.example.crm_project.modules.activity_management.application.dto.request.CreateActivityRequest;
import org.example.crm_project.modules.activity_management.application.dto.response.ActivityResponse;
import org.example.crm_project.modules.activity_management.application.service.ActivityService;
import org.example.crm_project.modules.activity_management.domain.repository.PagedResult;
import org.example.crm_project.modules.activity_management.domain.repository.Pagination;
import org.hibernate.query.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/activities")
@RequiredArgsConstructor
public class ActivityController {
    private final ActivityService activityService;

    @GetMapping
    public PagedResult<ActivityResponse> getAllActivities( // 1. Đổi Page -> PagedResult
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) Integer status,
            @RequestParam(required = false) String activityType,
            @RequestParam(required = false) Long performedBy,
            @RequestParam(required = false) Long relatedToId,
            @RequestParam(required = false) String relatedToType,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate) {

        // 2. Tạo Pagination (Đồ của Duy) thay vì Pageable (Đồ của Spring)
        Pagination pagination = new Pagination(page, size);

        // Giữ nguyên phần xử lý ngày tháng
        LocalDateTime startDateTime = (fromDate != null) ? fromDate.atStartOfDay() : null;
        LocalDateTime endDateTime = (toDate != null) ? toDate.atTime(LocalTime.MAX) : null;

        ActivitySearchCriteria criteria = new ActivitySearchCriteria(
                search, status, activityType, performedBy, relatedToId, relatedToType, startDateTime, endDateTime);

        // 3. Truyền pagination vào các hàm service
        if (search == null && status == null && activityType == null
                && performedBy == null && relatedToId == null && fromDate == null && toDate == null) {
            return activityService.getAll(pagination);
        }

        return activityService.filter(criteria, pagination);
    }

    @GetMapping("/{id}")
    public ActivityResponse getActivityById(@PathVariable Long id) {
        return activityService.getById(id);
    }

    // @PostMapping
    // public ActivityResponse createActivity(@RequestBody CreateActivityRequest
    // request) {

    // return service.create(request);
    // }
}