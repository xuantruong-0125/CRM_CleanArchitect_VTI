package org.example.crm_project.modules.products_managerment.application.service;

import lombok.RequiredArgsConstructor;
import org.example.crm_project.modules.products_managerment.application.dto.request.CreateProductRequest;
import org.example.crm_project.modules.products_managerment.application.dto.request.UpdateProductRequest;
import org.example.crm_project.modules.products_managerment.application.dto.response.PageResponse;
import org.example.crm_project.modules.products_managerment.application.dto.response.ProductResponse;
import org.example.crm_project.modules.products_managerment.application.interfaces.ProductService;
import org.example.crm_project.modules.products_managerment.application.mapper.ProductMapper;
import org.example.crm_project.modules.products_managerment.domain.entity.Category;
import org.example.crm_project.modules.products_managerment.domain.entity.Product;
import org.example.crm_project.modules.products_managerment.domain.exception.CategoryNotFoundException;
import org.example.crm_project.modules.products_managerment.domain.exception.ProductNotFoundException;
import org.example.crm_project.modules.products_managerment.domain.repository.CategoryRepository;
import org.example.crm_project.modules.products_managerment.domain.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Override
    @Transactional
    public ProductResponse create(CreateProductRequest req) {
        Category category = null;
        if (req.getCategoryId() != null) {
            category = categoryRepository.findById(req.getCategoryId())
                    .orElseThrow(() -> new CategoryNotFoundException(req.getCategoryId()));
        }

        Product product = Product.create(
                req.getSkuCode(),
                req.getName(),
                req.getImageUrl(),
                req.getDescription(),
                category,
                null // createdBy
        );

        return ProductMapper.toResponse(productRepository.save(product));
    }

    @Override
    @Transactional
    public ProductResponse update(Long id, UpdateProductRequest req) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));

        if (req.getCategoryId() != null) {
            Category category = categoryRepository.findById(req.getCategoryId())
                    .orElseThrow(() -> new CategoryNotFoundException(req.getCategoryId()));
            product.setCategory(category);
        }

        ProductMapper.updateEntity(req, product);
        
        return ProductMapper.toResponse(productRepository.save(product));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
        
        product.softDelete(null); // deletedBy
        productRepository.save(product);
    }

    @Override
    @Transactional
    public void deleteByIds(List<Long> ids) {
        for (Long id : ids) {
            delete(id);
        }
    }

    @Override
    public ProductResponse getById(Long id) {
        return productRepository.findById(id)
                .map(ProductMapper::toResponse)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

    @Override
    public List<ProductResponse> getAll() {
        return productRepository.findAll().stream()
                .map(ProductMapper::toResponse)
                .toList();
    }

    @Override
    public PageResponse<ProductResponse> search(String keyword, Long categoryId, Double minPrice, Double maxPrice, String status, int page, int size) {
        List<ProductResponse> items = productRepository.search(keyword, categoryId, minPrice, maxPrice, status, page, size).stream()
                .map(ProductMapper::toResponse)
                .toList();
        
        long total = productRepository.countSearch(keyword, categoryId, minPrice, maxPrice, status);
        
        return PageResponse.of(items, page, size, total);
    }

    @Override
    public boolean checkSkuCode(String skuCode, Long excludeId) {
        if (excludeId != null) {
            return productRepository.existsBySkuCodeExcludeId(skuCode, excludeId);
        }
        return productRepository.existsBySkuCode(skuCode);
    }
}
