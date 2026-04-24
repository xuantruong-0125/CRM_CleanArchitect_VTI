package org.example.crm_project.modules.activity_management.domain.repository;

import java.util.List;
import java.util.Optional;

import org.example.crm_project.modules.activity_management.application.dto.request.ActivitySearchCriteria;
import org.example.crm_project.modules.activity_management.domain.entity.Activity;

public interface ActivityRepository {

    Activity save(Activity activity);

    // Check tồn tại
    boolean existsById(Long id);

    // Lấy dòng thời gian hoạt động của một đối tượng cụ thể (Customer/Lead)
    List<Activity> findByRelatedObject(String type, Long id);

    void deleteById(Long id);
    void deleteBulk(List<Long> ids);
    void deleteAllByIdInBatch(List<Long> ids);
    PagedResult<Activity> findAll(Pagination pagination);

    Optional<Activity> findById(Long id);

    PagedResult<Activity> findByCriteria(ActivitySearchCriteria criteria, Pagination pagination);
}
