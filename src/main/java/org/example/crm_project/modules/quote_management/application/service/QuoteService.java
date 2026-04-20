package org.example.crm_project.modules.quote_management.application.service;

import lombok.RequiredArgsConstructor;
import org.example.crm_project.modules.quote_management.application.dto.request.CreateQuoteRequest;
import org.example.crm_project.modules.quote_management.application.dto.request.QuoteLineItemRequest;
import org.example.crm_project.modules.quote_management.application.dto.request.UpdateQuoteRequest;
import org.example.crm_project.modules.quote_management.application.dto.response.QuoteDetailResponse;
import org.example.crm_project.modules.quote_management.application.dto.response.QuoteLineItemResponse;
import org.example.crm_project.modules.quote_management.application.dto.response.QuoteResponse;
import org.example.crm_project.modules.quote_management.application.mapper.QuoteMapper;
import org.example.crm_project.modules.quote_management.domain.constant.TemplateType;
import org.example.crm_project.modules.quote_management.domain.entity.Customer;
import org.example.crm_project.modules.quote_management.domain.entity.DocumentTemplate;
import org.example.crm_project.modules.quote_management.domain.entity.Product;
import org.example.crm_project.modules.quote_management.domain.entity.Quote;
import org.example.crm_project.modules.quote_management.domain.entity.QuoteLineItem;
import org.example.crm_project.modules.quote_management.domain.exception.QuoteNotFoundException;
import org.example.crm_project.modules.quote_management.domain.repository.CustomerRepository;
import org.example.crm_project.modules.quote_management.domain.repository.DocumentTemplateRepository;
import org.example.crm_project.modules.quote_management.domain.repository.ProductRepository;
import org.example.crm_project.modules.quote_management.domain.repository.QuoteLineItemRepository;
import org.example.crm_project.modules.quote_management.domain.repository.QuoteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Application service cho module Báo Giá.
 * Điều phối các thao tác nghiệp vụ, không chứa logic DB trực tiếp.
 */
@Service
@RequiredArgsConstructor
public class QuoteService {

    private final QuoteRepository quoteRepository;
    private final QuoteLineItemRepository lineItemRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final DocumentTemplateRepository templateRepository;

    // ===== GET ALL =====
    public List<QuoteResponse> getAll(String keyword) {
        List<Quote> quotes = (keyword != null && !keyword.isBlank())
                ? quoteRepository.searchByKeyword(keyword.trim())
                : quoteRepository.findAllActive();

        return quotes.stream()
                .map(q -> {
                    String customerName = customerRepository.findById(q.getCustomerId())
                            .map(Customer::getName).orElse("");
                    String templateName = q.getTemplateId() != null
                            ? templateRepository.findById(q.getTemplateId())
                            .map(DocumentTemplate::getName).orElse("")
                            : "";
                    return QuoteMapper.toResponse(q, customerName, templateName);
                })
                .collect(Collectors.toList());
    }

    // ===== GET BY ID =====
    public QuoteDetailResponse getById(Integer id) {
        Quote quote = quoteRepository.findById(id)
                .orElseThrow(() -> new QuoteNotFoundException(id));

        String customerName = customerRepository.findById(quote.getCustomerId())
                .map(Customer::getName).orElse("");
        String templateName = quote.getTemplateId() != null
                ? templateRepository.findById(quote.getTemplateId())
                .map(DocumentTemplate::getName).orElse("")
                : "";

        List<QuoteLineItem> lineItems = lineItemRepository.findByQuoteId(id);
        List<QuoteLineItemResponse> lineItemResponses = buildLineItemResponses(lineItems);

        return QuoteMapper.toDetailResponse(quote, customerName, templateName, lineItemResponses);
    }

    // ===== CREATE =====
    @Transactional
    public QuoteDetailResponse create(CreateQuoteRequest req) {
        // 🔥 Kiểm tra số báo giá trùng
        if (quoteRepository.existsByQuoteNumber(req.getQuoteNumber())) {
            throw new RuntimeException("Số báo giá đã tồn tại: " + req.getQuoteNumber());
        }

        Quote quote = QuoteMapper.toEntity(req);
        Quote saved = quoteRepository.save(quote);

        saveLineItems(saved.getId(), req.getLineItems());

        return getById(saved.getId());
    }

    // ===== UPDATE =====
    @Transactional
    public QuoteDetailResponse update(Integer id, UpdateQuoteRequest req) {
        Quote quote = quoteRepository.findById(id)
                .orElseThrow(() -> new QuoteNotFoundException(id));

        // 🔥 Kiểm tra số báo giá trùng (trừ chính nó)
        if (!quote.getQuoteNumber().equalsIgnoreCase(req.getQuoteNumber())
                && quoteRepository.existsByQuoteNumberExcludeId(req.getQuoteNumber(), id)) {
            throw new RuntimeException("Số báo giá đã tồn tại: " + req.getQuoteNumber());
        }

        quote.update(
                req.getQuoteNumber(),
                req.getCustomerId(),
                req.getOpportunityId(),
                req.getStatusId(),
                req.getCurrencyCode(),
                req.getExchangeRate(),
                req.getValidUntil(),
                req.getTemplateId(),
                req.getUpdatedBy()
        );
        quoteRepository.save(quote);

        // Xóa line items cũ và thêm mới
        lineItemRepository.deleteByQuoteId(id);
        saveLineItems(id, req.getLineItems());

        return getById(id);
    }

    // ===== SOFT DELETE =====
    @Transactional
    public void softDelete(Integer id) {
        Quote quote = quoteRepository.findById(id)
                .orElseThrow(() -> new QuoteNotFoundException(id));
        quote.softDelete();
        quoteRepository.delete(quote);
    }

    // ===== CHECK QUOTE NUMBER =====
    public boolean isQuoteNumberExists(String quoteNumber, Integer excludeId) {
        if (excludeId == null) {
            return quoteRepository.existsByQuoteNumber(quoteNumber);
        }
        return quoteRepository.existsByQuoteNumberExcludeId(quoteNumber, excludeId);
    }

    // ===== GET FORM DATA (cho dropdown) =====
    public List<Customer> getAllActiveCustomers() {
        return customerRepository.findAllActive();
    }

    public List<Product> getAllActiveProducts() {
        return productRepository.findAllActive();
    }

    public List<DocumentTemplate> getQuoteTemplates() {
        return templateRepository.findByTypeAndIsActiveTrue(TemplateType.QUOTE);
    }

    // ===== PRIVATE HELPERS =====

    private void saveLineItems(Integer quoteId, List<QuoteLineItemRequest> requests) {
        if (requests == null || requests.isEmpty()) return;
        List<QuoteLineItem> items = requests.stream()
                .map(QuoteMapper::toLineItemEntity)
                .peek(item -> item.assignToQuote(quoteId))
                .collect(Collectors.toList());
        lineItemRepository.saveAll(items);
    }

    private List<QuoteLineItemResponse> buildLineItemResponses(List<QuoteLineItem> lineItems) {
        return lineItems.stream()
                .map(item -> {
                    String productName = productRepository.findById(item.getProductId())
                            .map(Product::getName).orElse("");
                    return QuoteMapper.toLineItemResponse(item, productName);
                })
                .collect(Collectors.toList());
    }
}
