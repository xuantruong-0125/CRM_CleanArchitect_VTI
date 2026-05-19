package org.example.crm_project.modules.activity_management.presentation.controller;

import lombok.RequiredArgsConstructor;

import org.example.crm_project.modules.activity_management.application.dto.request.ActivitySearchCriteria;
import org.example.crm_project.modules.activity_management.application.dto.request.CreateActivityRequest;
import org.example.crm_project.modules.activity_management.application.dto.request.UpdateActivityRequest;
import org.example.crm_project.modules.activity_management.application.dto.response.ActivityResponse;
import org.example.crm_project.modules.activity_management.application.service.contracts.ActivityService;
import org.example.crm_project.modules.activity_management.domain.repository.PagedResult;
import org.example.crm_project.modules.activity_management.domain.repository.Pagination;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

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
    @PreAuthorize("hasAuthority('ACTIVITY_VIEW')")
    public PagedResult<ActivityResponse> getAllActivities(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) Integer status,
            @RequestParam(required = false) String activityType,
            @RequestParam(required = false) Long performedBy,
            @RequestParam(required = false) Long relatedToId,
            @RequestParam(required = false) String relatedToType,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate,
            @RequestParam(required = false) Boolean important) {

        // 2. Tạo Pagination (Đồ của Duy) thay vì Pageable (Đồ của Spring)
        Pagination pagination = new Pagination(page, size);

        // Giữ nguyên phần xử lý ngày tháng
        LocalDateTime startDateTime = (fromDate != null) ? fromDate.atStartOfDay() : null;
        LocalDateTime endDateTime = (toDate != null) ? toDate.atTime(LocalTime.MAX) : null;

        ActivitySearchCriteria criteria = new ActivitySearchCriteria(
                search,
                status,
                activityType,
                performedBy,
                relatedToId,
                relatedToType,
                startDateTime,
                endDateTime,
                important);

        // 3. Truyền pagination vào các hàm service
        if (search == null && status == null && activityType == null
                && performedBy == null && relatedToId == null && fromDate == null && toDate == null
                && relatedToType == null && important == null) {
            return activityService.getAll(pagination);
        }

        return activityService.filter(criteria, pagination);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ACTIVITY_VIEW')")
    public ActivityResponse getActivityById(@PathVariable Long id) {
        return activityService.getById(id);
    }

    // Thêm mới Activity
    @PostMapping
    @PreAuthorize("hasAuthority('ACTIVITY_CREATE')")
    public ResponseEntity<ActivityResponse> createActivity(@Valid @RequestBody CreateActivityRequest request) {
        // Gọi Service xử lý logic lưu vào DB
        ActivityResponse createdActivity = activityService.create(request);

        // Trả về HTTP Status 201 (Created) kèm dữ liệu vừa tạo
        return ResponseEntity.status(HttpStatus.CREATED).body(createdActivity);
    }

    // KIỂU 1: Xóa đơn (Bấm vào chi tiết rồi xóa)
    // URL: DELETE /api/v1/activities/{id}
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ACTIVITY_DELETE')")
    @CacheEvict(value = "activities", allEntries = true)
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        activityService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // KIỂU 2: Xóa hàng loạt (Trang danh sách, chọn nhiều rồi xóa)
    // URL: DELETE /api/v1/activities
    @DeleteMapping
    @PreAuthorize("hasAuthority('ACTIVITY_DELETE')")
    @CacheEvict(value = "activities", allEntries = true)
    public ResponseEntity<String> deleteBulk(@RequestBody List<Long> ids) {
        activityService.deleteBulk(ids);
        return ResponseEntity.ok("Đã xóa thành công " + ids.size() + " dòng");
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ACTIVITY_UPDATE')")
    @CacheEvict(value = "activities", allEntries = true)
    public ResponseEntity<ActivityResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody UpdateActivityRequest request) {

        // Gọi xuống Service để xử lý
        ActivityResponse updated = activityService.update(id, request);

        return ResponseEntity.ok(updated);
    }

}