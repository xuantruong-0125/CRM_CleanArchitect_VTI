package org.example.crm_project.modules.quote_management.application.service;

import lombok.RequiredArgsConstructor;
import java.math.BigDecimal;
import org.example.crm_project.modules.quote_management.application.dto.request.CreateQuoteRequest;
import org.example.crm_project.modules.quote_management.application.dto.request.QuoteLineItemRequest;
import org.example.crm_project.modules.quote_management.application.dto.request.UpdateQuoteRequest;
import org.example.crm_project.modules.quote_management.application.dto.response.QuoteDetailResponse;
import org.example.crm_project.modules.quote_management.application.dto.response.QuoteLineItemResponse;
import org.example.crm_project.modules.quote_management.application.dto.response.QuoteResponse;
import org.example.crm_project.modules.quote_management.application.mapper.QuoteMapper;
import org.example.crm_project.modules.quote_management.domain.constant.TemplateType;
import org.example.crm_project.modules.customers_managerment.domain.entity.Customer;
import org.example.crm_project.modules.quote_management.domain.entity.DocumentTemplate;
import org.example.crm_project.modules.quote_management.domain.entity.Product;
import org.example.crm_project.modules.quote_management.domain.entity.Quote;
import org.example.crm_project.modules.quote_management.domain.entity.QuoteLineItem;
import org.example.crm_project.modules.quote_management.domain.exception.QuoteNotFoundException;
import org.example.crm_project.modules.customers_managerment.domain.repository.CustomerRepository;
import org.example.crm_project.modules.quote_management.domain.repository.DocumentTemplateRepository;
import org.example.crm_project.modules.quote_management.domain.repository.ProductRepository;
import org.example.crm_project.modules.quote_management.domain.repository.QuoteLineItemRepository;
import org.example.crm_project.modules.quote_management.domain.repository.QuoteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.example.crm_project.shared.model.PageResult;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Objects;
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

        // SEARCH (PAGINATED)
        public PageResult<QuoteResponse> search(String keyword, Long customerId, Integer statusId, String quoteNumber,
                        java.time.LocalDateTime fromDate, java.time.LocalDateTime toDate, int page, int size) {
                PageResult<Quote> quotesPage = quoteRepository.searchQuotes(keyword, customerId, statusId, quoteNumber,
                                fromDate, toDate, page, size);
                return toResponsePage(quotesPage);
        }

        // GET ALL (PAGINATED)
        public PageResult<QuoteResponse> getAll(String keyword, int page, int size) {
                PageResult<Quote> quotesPage = (keyword != null && !keyword.isBlank())
                                ? quoteRepository.searchByKeyword(keyword.trim(), page, size)
                                : quoteRepository.findAllActive(page, size);
                return toResponsePage(quotesPage);
        }

        // GET BY CUSTOMER (PAGINATED)
        public PageResult<QuoteResponse> getByCustomer(Long customerId, int page, int size) {
                PageResult<Quote> quotesPage = quoteRepository.findActiveByCustomerId(customerId, page, size);
                return toResponsePage(quotesPage);
        }

        /**
         * Chuyển đổi PageResult<Quote> sang PageResult<QuoteResponse> với tối ưu N+1 query.
         */
        private PageResult<QuoteResponse> toResponsePage(PageResult<Quote> quotesPage) {
                List<QuoteResponse> responses = enrichQuotesWithMetadata(quotesPage.getData());

                return PageResult.<QuoteResponse>builder()
                                .data(responses)
                                .currentPage(quotesPage.getCurrentPage())
                                .pageSize(quotesPage.getPageSize())
                                .totalElements(quotesPage.getTotalElements())
                                .totalPages(quotesPage.getTotalPages())
                                .hasNext(quotesPage.isHasNext())
                                .hasPrevious(quotesPage.isHasPrevious())
                                .build();
        }

        /**
         * Làm giàu dữ liệu cho danh sách Quote bằng cách fetch metadata (khách hàng, template) theo lô.
         * Giảm số lượng query từ N+1 xuống O(1) cho metadata.
         */
        private List<QuoteResponse> enrichQuotesWithMetadata(List<Quote> quotes) {
                if (quotes == null || quotes.isEmpty()) {
                        return List.of();
                }

                // 1. Thu thập tất cả ID cần thiết
                Set<Long> customerIds = quotes.stream()
                                .map(Quote::getCustomerId)
                                .filter(Objects::nonNull)
                                .collect(Collectors.toSet());

                Set<Integer> templateIds = quotes.stream()
                                .map(Quote::getTemplateId)
                                .filter(Objects::nonNull)
                                .collect(Collectors.toSet());

                // 2. Fetch metadata theo lô (Batch fetching)
                Map<Long, String> customerNames = customerRepository.findByIds(customerIds).stream()
                                .collect(Collectors.toMap(Customer::getId, Customer::getName, (a, b) -> a));

                Map<Integer, String> templateNames = templateRepository.findByIds(templateIds).stream()
                                .collect(Collectors.toMap(DocumentTemplate::getId, DocumentTemplate::getName, (a, b) -> a));

                // 3. Map sang Response DTO
                return quotes.stream()
                                .map(q -> {
                                        String customerName = customerNames.getOrDefault(q.getCustomerId(), "");
                                        String templateName = q.getTemplateId() != null
                                                        ? templateNames.getOrDefault(q.getTemplateId(), "")
                                                        : "";
                                        return QuoteMapper.toResponse(q, customerName, templateName);
                                })
                                .collect(Collectors.toList());
        }

        // GET BY ID
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

        @Transactional
        public QuoteDetailResponse create(CreateQuoteRequest req) {
                // 1. Tạo entity với quoteNumber tạm thời (sẽ được cập nhật sau)
                Quote quote = QuoteMapper.toEntity(req);

                // 2. Lưu Quote để lấy ID (quoteNumber lúc này là "TEMP")
                Quote saved = quoteRepository.save(quote);

                // 3. Auto-generate quoteNumber = "BG-{id}"
                String generatedNumber = "BG-" + saved.getId();
                quoteRepository.updateQuoteNumber(saved.getId(), generatedNumber);

                // 4. Lưu danh sách line items
                saveLineItems(saved.getId(), req.getLineItems());

                // 5. Cập nhật totalAmount bằng @Modifying để đảm bảo ghi xuống DB
                BigDecimal totalAmount = req.getTotalAmount() != null ? req.getTotalAmount() : BigDecimal.ZERO;
                quoteRepository.updateTotalAmount(saved.getId(), totalAmount);

                return getById(saved.getId());
        }

        // UPDATE
        @Transactional
        public QuoteDetailResponse update(Integer id, UpdateQuoteRequest req) {
                Quote quote = quoteRepository.findById(id)
                                .orElseThrow(() -> new QuoteNotFoundException(id));

                // Kiểm tra số báo giá trùng (trừ chính nó)
                if (!quote.getQuoteNumber().equalsIgnoreCase(req.getQuoteNumber())
                                && quoteRepository.existsByQuoteNumberExcludeId(req.getQuoteNumber(), id)) {
                        throw new RuntimeException("Số báo giá đã tồn tại: " + req.getQuoteNumber());
                }

                // 1. Lấy totalAmount từ request (frontend đã tính sẵn)
                BigDecimal totalAmount = req.getTotalAmount() != null ? req.getTotalAmount() : BigDecimal.ZERO;

                // 2. Cập nhật thông tin cơ bản + totalAmount
                quote.update(
                                req.getQuoteNumber(),
                                req.getCustomerId(),
                                req.getOpportunityId(),
                                req.getStatusId(),
                                req.getCurrencyCode(),
                                req.getExchangeRate(),
                                req.getValidUntil(),
                                req.getTemplateId(),
                                req.getUpdatedBy());
                quote.setTotalAmount(totalAmount);

                // 3. Lưu quote
                quoteRepository.save(quote);

                // 4. Xóa line items cũ và thêm mới
                lineItemRepository.deleteByQuoteId(id);
                saveLineItems(id, req.getLineItems());

                // 5. Cập nhật lại totalAmount bằng @Modifying để đảm bảo ghi xuống DB
                quoteRepository.updateTotalAmount(id, totalAmount);

                return getById(id);
        }

        private BigDecimal calculateTotalFromItems(List<QuoteLineItemRequest> lineItems) {
                return BigDecimal.ZERO; // Không dùng nữa, giữ lại để không lỗi compile nếu có nơi gọi
        }

        // SOFT DELETE
        @Transactional
        public void softDelete(Integer id) {
                Quote quote = quoteRepository.findById(id)
                                .orElseThrow(() -> new QuoteNotFoundException(id));
                quote.softDelete();
                quoteRepository.delete(quote);
        }

        // CHECK QUOTE NUMBER
        public boolean isQuoteNumberExists(String quoteNumber, Integer excludeId) {
                if (excludeId == null) {
                        return quoteRepository.existsByQuoteNumber(quoteNumber);
                }
                return quoteRepository.existsByQuoteNumberExcludeId(quoteNumber, excludeId);
        }

        // GET FORM DATA (cho dropdown)
        public List<Customer> getAllActiveCustomers() {
                return customerRepository.findAllActive();
        }

        public List<Customer> searchCustomers(String keyword) {
                return customerRepository.searchCustomers(keyword, 10);
        }

        public List<Product> getAllActiveProducts() {
                return productRepository.findAllActive();
        }

        public List<DocumentTemplate> getQuoteTemplates() {
                return templateRepository.findByTypeAndIsActiveTrue(TemplateType.QUOTE);
        }

        // PRIVATE HELPERS


        private void saveLineItems(Integer quoteId, List<QuoteLineItemRequest> requests) {
                if (requests == null || requests.isEmpty())
                        return;
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
