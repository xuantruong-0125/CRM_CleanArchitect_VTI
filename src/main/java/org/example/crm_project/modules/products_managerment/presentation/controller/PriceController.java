package org.example.crm_project.modules.products_managerment.presentation.controller;

import lombok.RequiredArgsConstructor;
import org.example.crm_project.modules.products_managerment.application.dto.request.CreatePriceRequest;
import org.example.crm_project.modules.products_managerment.application.dto.request.UpdatePriceRequest;
import org.example.crm_project.modules.products_managerment.application.dto.response.PageResponse;
import org.example.crm_project.modules.products_managerment.application.dto.response.PriceResponse;
import org.example.crm_project.modules.products_managerment.application.interfaces.PriceService;
import org.example.crm_project.modules.products_managerment.presentation.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/prices")
@RequiredArgsConstructor
public class PriceController {

    private final PriceService priceService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<PriceResponse> create( @RequestBody CreatePriceRequest request) {
        return ApiResponse.success("Price created successfully", priceService.create(request));
    }

    @PutMapping("/{id}")
    public ApiResponse<PriceResponse> update(@PathVariable Long id, @RequestBody UpdatePriceRequest request) {
        return ApiResponse.success("Price updated successfully", priceService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        priceService.delete(id);
        return ApiResponse.success("Price deleted successfully", null);
    }

    @DeleteMapping("/bulk")
    public ApiResponse<Void> deleteBulk(@RequestBody List<Long> ids) {
        priceService.deleteByIds(ids);
        return ApiResponse.success("Prices deleted successfully", null);
    }

    @GetMapping("/{id}")
    public ApiResponse<PriceResponse> getById(@PathVariable Long id) {
        return ApiResponse.success(priceService.getById(id));
    }

    @GetMapping
    public ApiResponse<List<PriceResponse>> getAll() {
        return ApiResponse.success(priceService.getAll());
    }

    @GetMapping("/product/{productId}")
    public ApiResponse<List<PriceResponse>> getByProductId(@PathVariable Long productId) {
        return ApiResponse.success(priceService.getByProductId(productId));
    }

    @GetMapping("/search")
    public ApiResponse<PageResponse<PriceResponse>> search(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Long productId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ApiResponse.success(priceService.search(keyword, productId, page, size));
    }
}
