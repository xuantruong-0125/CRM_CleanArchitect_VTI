package org.example.crm_project.modules.products_managerment.application.service;

import lombok.RequiredArgsConstructor;
import org.example.crm_project.modules.products_managerment.application.dto.request.CreatePriceRequest;
import org.example.crm_project.modules.products_managerment.application.dto.request.UpdatePriceRequest;
import org.example.crm_project.modules.products_managerment.application.dto.response.PageResponse;
import org.example.crm_project.modules.products_managerment.application.dto.response.PriceResponse;
import org.example.crm_project.modules.products_managerment.application.interfaces.PriceService;
import org.example.crm_project.modules.products_managerment.application.mapper.PriceMapper;
import org.example.crm_project.modules.products_managerment.domain.entity.Price;
import org.example.crm_project.modules.products_managerment.domain.entity.Product;
import org.example.crm_project.modules.products_managerment.domain.exception.PriceNotFoundException;
import org.example.crm_project.modules.products_managerment.domain.exception.ProductNotFoundException;
import org.example.crm_project.modules.products_managerment.domain.repository.PriceRepository;
import org.example.crm_project.modules.products_managerment.domain.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PriceServiceImpl implements PriceService {

    private final PriceRepository priceRepository;
    private final ProductRepository productRepository;

    @Override
    @Transactional
    public PriceResponse create(CreatePriceRequest req) {
        Product product = productRepository.findById(req.getProductId())
                .orElseThrow(() -> new ProductNotFoundException(req.getProductId()));

        // Deactivate existing active prices for this product
        List<Price> activePrices = priceRepository.findByProductId(product.getId()).stream()
                .filter(p -> Boolean.TRUE.equals(p.getActive()))
                .toList();
        
        for (Price p : activePrices) {
            p.setActive(false);
            priceRepository.save(p);
        }

        Price price = Price.create(
                req.getBasePrice(),
                req.getTaxRate(),
                req.getEffectiveFrom(),
                req.getEffectiveTo(),
                product,
                null // createdBy
        );

        return PriceMapper.toResponse(priceRepository.save(price));
    }

    @Override
    @Transactional
    public PriceResponse update(Long id, UpdatePriceRequest req) {
        Price price = priceRepository.findById(id)
                .orElseThrow(() -> new PriceNotFoundException(id));

        PriceMapper.updateEntity(req, price);
        
        return PriceMapper.toResponse(priceRepository.save(price));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Price price = priceRepository.findById(id)
                .orElseThrow(() -> new PriceNotFoundException(id));
        price.softDelete(null); // deletedBy
        priceRepository.save(price);
    }

    @Override
    @Transactional
    public void deleteByIds(List<Long> ids) {
        for (Long id : ids) {
            delete(id);
        }
    }

    @Override
    public PriceResponse getById(Long id) {
        return priceRepository.findById(id)
                .map(PriceMapper::toResponse)
                .orElseThrow(() -> new PriceNotFoundException(id));
    }

    @Override
    public List<PriceResponse> getByProductId(Long productId) {
        return priceRepository.findByProductId(productId).stream()
                .map(PriceMapper::toResponse)
                .toList();
    }

    @Override
    public List<PriceResponse> getAll() {
        return priceRepository.findAll().stream()
                .map(PriceMapper::toResponse)
                .toList();
    }

    @Override
    public PageResponse<PriceResponse> search(String keyword, Long productId, int page, int size) {
        List<PriceResponse> items = priceRepository.search(keyword, productId, page, size).stream()
                .map(PriceMapper::toResponse)
                .toList();
        
        long totalItems = priceRepository.countSearch(keyword, productId);
        int totalPages = (int) Math.ceil((double) totalItems / size);

        return PageResponse.<PriceResponse>builder()
                .items(items)
                .page(page)
                .size(size)
                .totalItems(totalItems)
                .totalPages(totalPages)
                .build();
    }
}
