package org.example.crm_project.modules.products_managerment.application.interfaces;

import org.example.crm_project.modules.products_managerment.application.dto.request.CreateProductRequest;
import org.example.crm_project.modules.products_managerment.application.dto.request.UpdateProductRequest;
import org.example.crm_project.modules.products_managerment.application.dto.response.PageResponse;
import org.example.crm_project.modules.products_managerment.application.dto.response.ProductResponse;
import org.example.crm_project.modules.products_managerment.application.interfaces.BaseService;

public interface ProductService extends BaseService<CreateProductRequest, UpdateProductRequest, ProductResponse, Long> {
    PageResponse<ProductResponse> search(String keyword, Long categoryId, int page, int size);
    boolean checkSkuCode(String skuCode, Long excludeId);
}
