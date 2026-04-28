package org.example.crm_project.modules.products_managerment.application.interfaces;

import org.example.crm_project.modules.products_managerment.application.dto.request.CreatePriceRequest;
import org.example.crm_project.modules.products_managerment.application.dto.request.UpdatePriceRequest;
import org.example.crm_project.modules.products_managerment.application.dto.response.PageResponse;
import org.example.crm_project.modules.products_managerment.application.dto.response.PriceResponse;
import org.example.crm_project.modules.products_managerment.application.interfaces.BaseService;

import java.util.List;

public interface PriceService extends BaseService<CreatePriceRequest, UpdatePriceRequest, PriceResponse, Long> {
    List<PriceResponse> getByProductId(Long productId);

    PageResponse<PriceResponse> search(String keyword, Long productId, int page, int size);
}
