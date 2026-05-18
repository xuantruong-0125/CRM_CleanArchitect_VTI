package org.example.crm_project.modules.products_managerment.presentation.controller;

import lombok.RequiredArgsConstructor;
import org.example.crm_project.modules.products_managerment.application.dto.request.CreateCategoryRequest;
import org.example.crm_project.modules.products_managerment.application.dto.request.UpdateCategoryRequest;
import org.example.crm_project.modules.products_managerment.application.dto.response.CategoryResponse;
import org.example.crm_project.modules.products_managerment.application.dto.response.PageResponse;
import org.example.crm_project.modules.products_managerment.application.interfaces.CategoryService;
import org.example.crm_project.modules.products_managerment.presentation.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<CategoryResponse> create(@RequestBody CreateCategoryRequest request) {
        return ApiResponse.success("Category created successfully", categoryService.create(request));
    }

    @PutMapping("/{id}")
    public ApiResponse<CategoryResponse> update(@PathVariable Long id, @RequestBody UpdateCategoryRequest request) {
        return ApiResponse.success("Category updated successfully", categoryService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        categoryService.delete(id);
        return ApiResponse.success("Category deleted successfully", null);
    }

    @DeleteMapping("/bulk")
    public ApiResponse<Void> deleteBulk(@RequestBody List<Long> ids) {
        categoryService.deleteByIds(ids);
        return ApiResponse.success("Categories deleted successfully", null);
    }

    @GetMapping("/{id}")
    public ApiResponse<CategoryResponse> getById(@PathVariable Long id) {
        return ApiResponse.success(categoryService.getById(id));
    }

    @GetMapping
    public ApiResponse<List<CategoryResponse>> getAll() {
        return ApiResponse.success(categoryService.getAll());
    }

    @GetMapping("/search")
    public ApiResponse<PageResponse<CategoryResponse>> search(
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ApiResponse.success(categoryService.search(keyword, page, size));
    }
}
