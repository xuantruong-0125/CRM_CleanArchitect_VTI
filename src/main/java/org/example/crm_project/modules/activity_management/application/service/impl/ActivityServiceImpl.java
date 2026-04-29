package org.example.crm_project.modules.activity_management.application.service.impl;

import lombok.RequiredArgsConstructor;

import org.example.crm_project.modules.activity_management.application.dto.request.ActivitySearchCriteria;
import org.example.crm_project.modules.activity_management.application.dto.request.CreateActivityRequest;
import org.example.crm_project.modules.activity_management.application.dto.request.UpdateActivityRequest;
import org.example.crm_project.modules.activity_management.application.dto.response.ActivityResponse;
import org.example.crm_project.modules.activity_management.application.mapper.ActivityMapper;
import org.example.crm_project.modules.activity_management.application.service.contracts.ActivityService;
import org.example.crm_project.modules.activity_management.domain.entity.Activity;
import org.example.crm_project.modules.activity_management.domain.repository.ActivityRepository;
import org.example.crm_project.modules.activity_management.domain.repository.ActivityUserProvider;
import org.example.crm_project.modules.activity_management.domain.repository.PagedResult;
import org.example.crm_project.modules.activity_management.domain.repository.Pagination;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.cache.annotation.CacheEvict;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ActivityServiceImpl implements ActivityService {

     private final ActivityRepository repository;
    private final ActivityUserProvider userProvider;

    // 1. THÊM MỚI
    @CacheEvict(value = "activities", allEntries = true)
    @Transactional
    public ActivityResponse create(CreateActivityRequest request) {
        // Chuyển từ Request -> Domain Entity
        Activity activity = ActivityMapper.toEntity(request);

        // Lưu vào Database thông qua Repository
        Activity savedActivity = repository.save(activity);

        // Lấy tên người thực hiện để trả về Response
        String userName = userProvider.getUserFullNameById(savedActivity.getPerformedBy());

        return ActivityMapper.toResponse(savedActivity, userName);
    }

    // 2. CẬP NHẬT
    @CacheEvict(value = "activities", allEntries = true)
    @Transactional
    public ActivityResponse update(Long id, UpdateActivityRequest request) {
        // Tìm hoạt động cũ, nếu không thấy thì báo lỗi
        Activity existingActivity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy hoạt động ID: " + id));

        // Cập nhật các thông tin mới từ request vào Entity
        existingActivity.updateInfo(
                request.getSubject(),
                request.getDescription(),
                request.getStatus(),
                request.getStartDate(),     // Bổ sung
                request.getEndDate(),       // Bổ sung
                request.getCompletedAt(),   // Bổ sung
                request.getOutcome(),       // Bổ sung
                request.getIsImportant()    // Bổ sung
        );

        Activity updatedActivity = repository.save(existingActivity);
        String userName = userProvider.getUserFullNameById(updatedActivity.getPerformedBy());

        return ActivityMapper.toResponse(updatedActivity, userName);
    }

    // 3. XÓA
    @CacheEvict(value = "activities", allEntries = true)
    @Transactional
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Không thể xóa, không tìm thấy hoạt động ID: " + id);
        }
        repository.deleteById(id);
    }

    @Cacheable(value = "activities", key = "#p0?.page() + '-' + #p0?.size()")
    public PagedResult<ActivityResponse> getAll(Pagination pagination) {
        // 1. Gọi Repo lấy PagedResult (đã có content, totalElements, totalPages)
        PagedResult<Activity> pagedActivities = repository.findAll(pagination);

        // 2. Chuyển đổi từ Domain Entity sang Response DTO
        List<ActivityResponse> responses = pagedActivities.content().stream()
                .map(activity -> {
                    String userName = userProvider.getUserFullNameById(activity.getPerformedBy());
                    return ActivityMapper.toResponse(activity, userName);
                })
                .toList();

        // 3. Trả về PagedResult mới chứa danh sách Response
        return new PagedResult<>(
                responses,
                pagedActivities.totalElements(),
                pagedActivities.totalPages());
    }

    public ActivityResponse getById(Long id) {

        Activity activity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Not found"));

        // GIẢ SỬ: Sau này Duy gọi sang UserModule để lấy tên theo ID
        // Hiện tại mình giả lập tên là "Admin" để Duy test API trước
        String employeeName = userProvider.getUserFullNameById(activity.getPerformedBy());

        return ActivityMapper.toResponse(activity, employeeName);
    }

    public PagedResult<ActivityResponse> filter(ActivitySearchCriteria criteria, Pagination pagination) {
        // 1. Gọi Repo với criteria và pagination
        PagedResult<Activity> pagedActivities = repository.findByCriteria(criteria, pagination);

        // 2. Map sang Response
        List<ActivityResponse> responses = pagedActivities.content().stream()
                .map(activity -> {
                    String userName = userProvider.getUserFullNameById(activity.getPerformedBy());
                    return ActivityMapper.toResponse(activity, userName);
                })
                .toList();

        return new PagedResult<>(
                responses,
                pagedActivities.totalElements(),
                pagedActivities.totalPages());
    }

    // 4. XÓA HÀNG LOẠT
    @CacheEvict(value = "activities", allEntries = true)
    @Transactional
    public void deleteBulk(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return;
        }
        // Duy dùng phương thức InBatch để xóa cực nhanh cho danh sách lớn
        repository.deleteAllByIdInBatch(ids);
    }

    // @Override
    // @Transactional
    // @CacheEvict(value = "activities", allEntries = true) // Xóa cache để trang danh sách cập nhật data mới
    // public ActivityResponse update(Long id, UpdateActivityRequest request) {
    //     // 1. Tìm bản ghi cũ trong DB
    //     Activity existingActivity = repository.findById(id)
    //             .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy hoạt động để cập nhật: " + id));

    //     // 2. Cập nhật thông tin (Duy gọi hàm updateInfo trong Entity)
    //     existingActivity.updateInfo(
    //             request.getSubject(),
    //             request.getDescription(),
    //             request.getStatus(),
    //             request.getActivityType()
    //             // Thêm các trường khác nếu cần như startDate, endDate...
    //     );

    //     // 3. Lưu vào MySQL
    //     Activity updatedActivity = repository.save(existingActivity);

    //     // 4. Lấy tên người dùng (từ Redis/UserModule) để đóng gói trả về
    //     String userName = userProvider.getUserFullNameById(updatedActivity.getPerformedBy());

    //     return ActivityMapper.toResponse(updatedActivity, userName);
    // }
    
}
