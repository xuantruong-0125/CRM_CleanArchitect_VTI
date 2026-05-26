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
import org.example.crm_project.modules.auth.domain.entity.AuthUser;
import org.example.crm_project.modules.note_management.Application.mapper.NoteMapper;
import org.example.crm_project.modules.note_management.Domain.entity.Note;
import org.example.crm_project.modules.note_management.Domain.repository.NoteRepository;
import org.example.crm_project.modules.system_managerment.application.service.UserService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private final NoteRepository noteRepository;
    private final UserService userService;

    private AuthUser getCurrentAuthenticatedUser() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getPrincipal() instanceof AuthUser) {
            return (AuthUser) auth.getPrincipal();
        }
        throw new AccessDeniedException("Phiên đăng nhập không hợp lệ hoặc đã hết hạn!");
    }

    // 1. THÊM MỚI
    @CacheEvict(value = "activities", allEntries = true)
    @Transactional
    public ActivityResponse create(CreateActivityRequest request) {

        AuthUser currentUser = getCurrentAuthenticatedUser();
        Long currentUserId = currentUser.getId();

        Long organizationId = null;
        try {
            var userDto = userService.getById(currentUserId);
            organizationId = userDto.getOrganizationId();

            System.out.println("=== DEBUG: Lấy được Org ID = " + organizationId + " của User ID = " + currentUserId);
        } catch (Exception e) {
            System.err.println("=== LỖI KHI LẤY USER THÔNG TIN ĐỂ ĐÓNG DẤU PHÒNG BAN: ===");
            e.printStackTrace();
        }

        // A. Lưu Activity trước để lấy được ID (notable_id)
        Activity activity = ActivityMapper.toEntity(request);
        activity.assignPerformedBy(currentUserId);
        activity.assignOrganizationId(organizationId);
        Activity savedActivity = repository.save(activity);

        // B. XỬ LÝ GHI CHÚ (NOTE)
        // Kiểm tra nếu Frontend có gửi nội dung ghi chú kèm theo
        if (request.getNoteContent() != null && !request.getNoteContent().trim().isEmpty()) {

            Note noteDomain = NoteMapper.toDomain(request, savedActivity.getId());
            noteDomain.assignCreatedBy(currentUserId);
            noteRepository.save(noteDomain);
        }

        // C. Trả về Response như cũ
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

        AuthUser currentUser = getCurrentAuthenticatedUser();
        if ("OWN".equals(currentUser.getScope()) && !currentUser.getId().equals(existingActivity.getPerformedBy())) {
            throw new AccessDeniedException("Bạn không có quyền chỉnh sửa hoạt động của người khác!");
        } else if ("BRANCH".equals(currentUser.getScope())) {
            try {
                var managerDto = userService.getById(currentUser.getId());
                if (existingActivity.getOrganizationId() == null
                        || !existingActivity.getOrganizationId().equals(managerDto.getOrganizationId())) {
                    throw new AccessDeniedException("Bạn không có quyền thao tác trên hoạt động của phòng ban khác!");
                }
            } catch (Exception e) {
            }
        }

        // Cập nhật các thông tin mới từ request vào Entity
        existingActivity.updateInfo(
                request.getSubject(),
                request.getDescription(),
                request.getStatus(),
                request.getStartDate(), // Bổ sung
                request.getEndDate(), // Bổ sung
                request.getCompletedAt(), // Bổ sung
                request.getOutcome(), // Bổ sung
                request.getIsImportant() // Bổ sung
        );

        Activity updatedActivity = repository.save(existingActivity);
        String userName = userProvider.getUserFullNameById(updatedActivity.getPerformedBy());

        return ActivityMapper.toResponse(updatedActivity, userName);
    }

    // 3. XÓA
    @CacheEvict(value = "activities", allEntries = true)
    @Transactional
    public void delete(Long id) {
        Activity existingActivity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy hoạt động ID: " + id));

        AuthUser currentUser = getCurrentAuthenticatedUser();
        if ("OWN".equals(currentUser.getScope()) && !currentUser.getId().equals(existingActivity.getPerformedBy())) {
            throw new AccessDeniedException("Bạn không có quyền xóa hoạt động của người khác!");
        } else if ("BRANCH".equals(currentUser.getScope())) {
            // VÁ LỖ HỔNG CHẶN SẾP SỬA/XÓA LÉN
            try {
                var managerDto = userService.getById(currentUser.getId());
                if (existingActivity.getOrganizationId() == null
                        || !existingActivity.getOrganizationId().equals(managerDto.getOrganizationId())) {
                    throw new AccessDeniedException("Bạn không có quyền thao tác trên hoạt động của phòng ban khác!");
                }
            } catch (Exception e) {
            }
        }

        repository.deleteById(id);
    }

    @Cacheable(value = "activities", key = "#p0?.page() + '-' + #p0?.size() + '-' + (T(org.springframework.security.core.context.SecurityContextHolder).getContext()?.authentication?.principal?.id ?: 0L)")
    public PagedResult<ActivityResponse> getAll(Pagination pagination) {

        AuthUser currentUser = getCurrentAuthenticatedUser();
        Long organizationId = null;
        try {
            var userDto = userService.getById(currentUser.getId());
            organizationId = userDto.getOrganizationId();
        } catch (Exception e) {
        }

        // 1. Gọi Repo lấy PagedResult (đã có content, totalElements, totalPages)
        PagedResult<Activity> pagedActivities = repository.findAll(pagination, currentUser.getId(),
                organizationId, currentUser.getScope());

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
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy hoạt động ID: " + id));

        AuthUser currentUser = getCurrentAuthenticatedUser();
        if ("OWN".equals(currentUser.getScope()) && !currentUser.getId().equals(activity.getPerformedBy())) {
            throw new AccessDeniedException("Bạn không có quyền xem hoạt động của người khác!");
        } else if ("BRANCH".equals(currentUser.getScope())) {
            try {
                var managerDto = userService.getById(currentUser.getId());
                if (activity.getOrganizationId() == null
                        || !activity.getOrganizationId().equals(managerDto.getOrganizationId())) {
                    throw new AccessDeniedException("Bạn không có quyền xem hoạt động của phòng ban khác!");
                }
            } catch (Exception e) {
            }
        }

        String employeeName = userProvider.getUserFullNameById(activity.getPerformedBy());

        return ActivityMapper.toResponse(activity, employeeName);
    }

    public PagedResult<ActivityResponse> filter(ActivitySearchCriteria criteria, Pagination pagination) {

        AuthUser currentUser = getCurrentAuthenticatedUser();
        Long organizationId = null;
        try {
            var userDto = userService.getById(currentUser.getId());
            organizationId = userDto.getOrganizationId();
        } catch (Exception e) {
        }

        // 1. Gọi Repo với criteria và pagination
        PagedResult<Activity> pagedActivities = repository.findByCriteria(criteria, pagination, currentUser.getId(),
                organizationId, currentUser.getScope());

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
        AuthUser currentUser = getCurrentAuthenticatedUser();
        Long managerOrgId = null;
        if ("BRANCH".equals(currentUser.getScope())) {
            try {
                managerOrgId = userService.getById(currentUser.getId()).getOrganizationId();
            } catch (Exception e) {
            }
        }

        for (Long id : ids) {
            Activity act = repository.findById(id).orElse(null);
            if (act != null) {
                if ("OWN".equals(currentUser.getScope()) && !currentUser.getId().equals(act.getPerformedBy())) {
                    throw new AccessDeniedException(
                            "Trong danh sách chọn có hoạt động của người khác, không thể xóa loạt!");
                } else if ("BRANCH".equals(currentUser.getScope())) {
                    if (act.getOrganizationId() == null || !act.getOrganizationId().equals(managerOrgId)) {
                        throw new AccessDeniedException(
                                "Trong danh sách chọn có hoạt động của phòng ban khác, không thể xóa loạt!");
                    }
                }
            }
        }
        // Duy dùng phương thức InBatch để xóa cực nhanh cho danh sách lớn
        repository.deleteAllByIdInBatch(ids);
    }
}
