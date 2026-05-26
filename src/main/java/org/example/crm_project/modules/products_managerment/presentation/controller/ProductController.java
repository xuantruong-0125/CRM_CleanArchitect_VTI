package org.example.crm_project.modules.products_managerment.presentation.controller;

import lombok.RequiredArgsConstructor;
import org.example.crm_project.modules.products_managerment.application.dto.request.CreateProductRequest;
import org.example.crm_project.modules.products_managerment.application.dto.request.UpdateProductRequest;
import org.example.crm_project.modules.products_managerment.application.dto.response.PageResponse;
import org.example.crm_project.modules.products_managerment.application.dto.response.ProductResponse;
import org.example.crm_project.modules.products_managerment.application.interfaces.ProductService;
import org.example.crm_project.modules.products_managerment.presentation.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<ProductResponse> create(@RequestBody CreateProductRequest request) {
        return ApiResponse.success("Product created successfully", productService.create(request));
    }

    @PutMapping("/{id}")
    public ApiResponse<ProductResponse> update(@PathVariable Long id, @RequestBody UpdateProductRequest request) {
        return ApiResponse.success("Product updated successfully", productService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        productService.delete(id);
        return ApiResponse.success("Product deleted successfully", null);
    }

    @DeleteMapping("/bulk")
    public ApiResponse<Void> deleteBulk(@RequestBody List<Long> ids) {
        productService.deleteByIds(ids);
        return ApiResponse.success("Products deleted successfully", null);
    }

    @GetMapping("/{id}")
    public ApiResponse<ProductResponse> getById(@PathVariable Long id) {
        return ApiResponse.success(productService.getById(id));
    }

    @GetMapping
    public ApiResponse<List<ProductResponse>> getAll() {
        return ApiResponse.success(productService.getAll());
    }

    @GetMapping("/search")
    public ApiResponse<PageResponse<ProductResponse>> search(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) String status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ApiResponse.success(productService.search(keyword, categoryId, minPrice, maxPrice, status, page, size));
    }

    @GetMapping("/check-sku")
    public ApiResponse<Boolean> checkSkuCode(
            @RequestParam String skuCode,
            @RequestParam(required = false) Long excludeId) {
        return ApiResponse.success(productService.checkSkuCode(skuCode, excludeId));
    }
}
