package org.example.crm_project.modules.activity_management.application.service.contracts;

import java.util.List;

import org.example.crm_project.modules.activity_management.application.dto.request.ActivitySearchCriteria;
import org.example.crm_project.modules.activity_management.application.dto.request.CreateActivityRequest;
import org.example.crm_project.modules.activity_management.application.dto.request.UpdateActivityRequest;
import org.example.crm_project.modules.activity_management.application.dto.response.ActivityResponse;
import org.example.crm_project.modules.activity_management.domain.repository.PagedResult;
import org.example.crm_project.modules.activity_management.domain.repository.Pagination;


public interface ActivityService {
    ActivityResponse create(CreateActivityRequest request);
    void delete(Long id);
    void deleteBulk(List<Long> ids); // Thêm hàm xóa hàng loạt
    PagedResult<ActivityResponse> getAll(Pagination pagination);
    ActivityResponse getById(Long id);
    PagedResult<ActivityResponse> filter(ActivitySearchCriteria criteria, Pagination pagination);
    ActivityResponse update(Long id, UpdateActivityRequest request);

}
