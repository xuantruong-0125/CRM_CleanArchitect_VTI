package org.example.crm_project.modules.products_managerment.application.service;

import lombok.RequiredArgsConstructor;
import org.example.crm_project.modules.products_managerment.application.dto.request.CreateCategoryRequest;
import org.example.crm_project.modules.products_managerment.application.dto.request.UpdateCategoryRequest;
import org.example.crm_project.modules.products_managerment.application.dto.response.CategoryResponse;
import org.example.crm_project.modules.products_managerment.application.dto.response.PageResponse;
import org.example.crm_project.modules.products_managerment.application.interfaces.CategoryService;
import org.example.crm_project.modules.products_managerment.application.mapper.CategoryMapper;
import org.example.crm_project.modules.products_managerment.domain.entity.Category;
import org.example.crm_project.modules.products_managerment.domain.exception.CategoryNotFoundException;
import org.example.crm_project.modules.products_managerment.domain.repository.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    @Transactional
    public CategoryResponse create(CreateCategoryRequest req) {
        Category category = CategoryMapper.toEntity(req);

        return CategoryMapper.toResponse(categoryRepository.save(category));
    }

    @Override
    @Transactional
    public CategoryResponse update(Long id, UpdateCategoryRequest req) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException(id));
        
        CategoryMapper.updateEntity(req, category);
        // set updatedBy if available
        
        return CategoryMapper.toResponse(categoryRepository.save(category));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException(id));
        
        category.softDelete(null); // deletedBy
        categoryRepository.save(category);
    }

    @Override
    @Transactional
    public void deleteByIds(List<Long> ids) {
        for (Long id : ids) {
            delete(id);
        }
    }

    @Override
    public CategoryResponse getById(Long id) {
        CategoryResponse res = categoryRepository.findById(id)
                .map(CategoryMapper::toResponse)
                .orElseThrow(() -> new CategoryNotFoundException(id));
        
        Map<Long, Integer> counts = categoryRepository.getProductCounts();
        res.setProductCount(counts.getOrDefault(id, 0));
        return res;
    }

    @Override
    public List<CategoryResponse> getAll() {
        Map<Long, Integer> counts = categoryRepository.getProductCounts();
        return categoryRepository.findAll().stream()
                .map(category -> {
                    CategoryResponse res = CategoryMapper.toResponse(category);
                    res.setProductCount(counts.getOrDefault(category.getId(), 0));
                    return res;
                })
                .toList();
    }

    @Override
    public PageResponse<CategoryResponse> search(String keyword, int page, int size) {
        Map<Long, Integer> counts = categoryRepository.getProductCounts();
        
        if (keyword == null || keyword.trim().isEmpty()) {
            return PageResponse.of(
                    categoryRepository.findAll(page, size).stream()
                            .map(category -> {
                                CategoryResponse res = CategoryMapper.toResponse(category);
                                res.setProductCount(counts.getOrDefault(category.getId(), 0));
                                return res;
                            })
                            .toList(),
                    page,
                    size,
                    categoryRepository.countActive()
            );
        }

        List<CategoryResponse> items = categoryRepository.search(keyword, page, size).stream()
                .map(category -> {
                    CategoryResponse res = CategoryMapper.toResponse(category);
                    res.setProductCount(counts.getOrDefault(category.getId(), 0));
                    return res;
                })
                .toList();

        long total = categoryRepository.countSearch(keyword);

        return PageResponse.of(items, page, size, total);
    }
}
