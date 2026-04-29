package org.example.crm_project.modules.activity_management.infrastructure.persistence.repository;

import lombok.RequiredArgsConstructor;

import org.example.crm_project.modules.activity_management.application.dto.request.ActivitySearchCriteria;
import org.example.crm_project.modules.activity_management.domain.entity.Activity;
import org.example.crm_project.modules.activity_management.domain.repository.ActivityRepository;
import org.example.crm_project.modules.activity_management.domain.repository.PagedResult;
import org.example.crm_project.modules.activity_management.domain.repository.Pagination;
import org.example.crm_project.modules.activity_management.infrastructure.persistence.entity.ActivityJpaEntity;
import org.example.crm_project.modules.activity_management.infrastructure.persistence.mapper.ActivityJpaMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ActivityRepositoryImpl implements ActivityRepository {

    private final JpaActivityRepository jpaRepository; // Spring Data JPA Interface

    @Override
    public Activity save(Activity activity) {
        var jpaEntity = ActivityJpaMapper.toJpa(activity); // Ép kiểu xuống JPA
        var savedEntity = jpaRepository.save(jpaEntity);// Lưu thực sự xuống MySQL
        return ActivityJpaMapper.toDomain(savedEntity);// Ép kiểu ngược lại Domain để báo cáo
    }

    @Override
    public Optional<Activity> findById(Long id) {
        return jpaRepository.findById(id).map(ActivityJpaMapper::toDomain);
    }

    @Override
    public boolean existsById(Long id) {
        return jpaRepository.existsById(id);
    }

    @Override
    public List<Activity> findByRelatedObject(String type, Long id) {
        return jpaRepository.findByRelatedToTypeAndRelatedToIdOrderByCreatedAtDesc(type, id)
                .stream()
                .map(ActivityJpaMapper::toDomain)
                .toList();
    }

    @Override
    public void deleteById(Long id) {
    jpaRepository.deleteById(id);
    }

    @Override
    public PagedResult<Activity> findAll(Pagination pagination) { // Đổi tham số thành Pagination
        // 1. Chuyển từ Pagination (Domain) -> Pageable (Spring)
        Pageable pageable = PageRequest.of(pagination.page(), pagination.size());

        // 2. Lấy Page từ Spring Data
        Page<ActivityJpaEntity> springPage = jpaRepository.findAll(pageable);

        // 3. Map sang Domain và đóng gói vào PagedResult của Duy
        List<Activity> content = springPage.getContent().stream()
                .map(ActivityJpaMapper::toDomain)
                .toList();

        return new PagedResult<>(content, springPage.getTotalElements(), springPage.getTotalPages());
    }

    @Override
    public PagedResult<Activity> findByCriteria(ActivitySearchCriteria criteria, Pagination pagination) { // Tham số
                                                                                                          // phải là
                                                                                                          // Pagination
        // 1. Chuyển từ Pagination (Domain) -> Pageable (Spring)
        Pageable pageable = PageRequest.of(pagination.page(), pagination.size());

        // 2. Làm sạch chuỗi (Giữ nguyên logic của Duy)
        String safeSearch = (criteria.search() != null && criteria.search().isBlank()) ? null : criteria.search();
        String safeRelatedToType = (criteria.relatedToType() != null && criteria.relatedToType().isBlank()) ? null
                : criteria.relatedToType();
        String safeActivityType = (criteria.activityType() != null && criteria.activityType().isBlank()) ? null
                : criteria.activityType();

        Integer typeAsNumber = null;
        if (safeActivityType != null) {
           try {
                typeAsNumber = org.example.crm_project.modules.activity_management.domain.constant.ActivityType
                        .valueOf(safeActivityType.toUpperCase())
                        .getValue();
            } catch (IllegalArgumentException e) {
                typeAsNumber = null; 
            }
        }

        // 3. Gọi JpaRepository lấy Page của Spring
        Page<ActivityJpaEntity> springPage = jpaRepository.searchActivities(
                safeSearch, 
                criteria.status(),
                 typeAsNumber, 
                 criteria.performedBy(),
                criteria.relatedToId(), 
                safeRelatedToType, 
                criteria.fromDate(), 
                criteria.toDate(),
                criteria.important(),
                pageable);

        // 4. "Đóng gói" lại thành PagedResult của Domain
        List<Activity> content = springPage.getContent().stream()
                .map(ActivityJpaMapper::toDomain)
                .toList();

        return new PagedResult<>(content, springPage.getTotalElements(), springPage.getTotalPages());
    }

    @Override
    public void deleteAllByIdInBatch(List<Long> ids) {
        jpaRepository.deleteAllByIdInBatch(ids);
    }

    @Override
    public void deleteBulk(List<Long> ids) {
        jpaRepository.deleteAllByIdInBatch(ids);
    }
}