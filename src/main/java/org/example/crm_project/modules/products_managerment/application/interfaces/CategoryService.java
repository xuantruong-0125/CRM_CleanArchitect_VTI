package org.example.crm_project.modules.products_managerment.application.interfaces;

import org.example.crm_project.modules.products_managerment.application.dto.request.CreateCategoryRequest;
import org.example.crm_project.modules.products_managerment.application.dto.request.UpdateCategoryRequest;
import org.example.crm_project.modules.products_managerment.application.dto.response.CategoryResponse;
import org.example.crm_project.modules.products_managerment.application.dto.response.PageResponse;
import org.example.crm_project.modules.products_managerment.application.interfaces.BaseService;

public interface CategoryService extends BaseService<CreateCategoryRequest, UpdateCategoryRequest, CategoryResponse, Long> {
    PageResponse<CategoryResponse> search(String keyword, int page, int size);
}
