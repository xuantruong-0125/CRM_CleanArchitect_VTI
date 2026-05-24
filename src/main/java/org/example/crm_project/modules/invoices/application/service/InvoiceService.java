package org.example.crm_project.modules.invoices.application.service;

import org.example.crm_project.modules.invoices.application.dto.request.BulkActionRequest;
import org.example.crm_project.modules.invoices.application.dto.request.CreateInvoiceRequest;
import org.example.crm_project.modules.invoices.application.dto.response.InvoiceResponse;
import org.example.crm_project.modules.invoices.domain.constant.InvoiceStatus;
import org.example.crm_project.modules.invoices.domain.entity.Invoice;
import org.example.crm_project.modules.invoices.domain.entity.InvoiceLineItem;
import org.example.crm_project.modules.invoices.infrastructure.client.PdfGenerationService;
import org.example.crm_project.modules.contracts.infrastructure.persistence.entity.DocumentTemplateJpaEntity;
import org.example.crm_project.modules.invoices.infrastructure.persistence.entity.InvoiceJpaEntity;
import org.example.crm_project.modules.orders.infrastructure.persistence.entity.OrderJpaEntity;
import org.example.crm_project.modules.invoices.infrastructure.persistence.mapper.InvoicePersistenceMapper;
import org.example.crm_project.modules.contracts.infrastructure.persistence.repository.DocumentTemplateJpaRepository;
import org.example.crm_project.modules.invoices.infrastructure.persistence.repository.InvoiceJpaRepository;
import org.example.crm_project.modules.invoices.infrastructure.persistence.repository.InvoiceSpecification;
import org.example.crm_project.modules.orders.infrastructure.persistence.repository.OrderJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InvoiceService {
    private final InvoiceJpaRepository jpaRepository;
    private final OrderJpaRepository orderJpaRepository;
    private final DocumentTemplateJpaRepository templateRepository;
    private final PdfGenerationService pdfGenerationService;
    private final InvoicePersistenceMapper mapper;

    public Page<InvoiceResponse> searchInvoices(
            String invoiceNumber, LocalDate issueDate, InvoiceStatus status, Long assignedTo, Pageable pageable) {
        Specification<InvoiceJpaEntity> spec = InvoiceSpecification.filter(invoiceNumber, issueDate, status, assignedTo);
        return jpaRepository.findAll(spec, pageable)
                .map(mapper::toDomainEntity)
                .map(this::mapToResponse);
    }

    public InvoiceResponse getInvoiceById(Long id) {
        return jpaRepository.findById(id)
                .map(mapper::toDomainEntity)
                .map(this::mapToResponse)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy hóa đơn"));
    }

    @Transactional
    public InvoiceResponse createInvoice(CreateInvoiceRequest request) {
        List<InvoiceLineItem> items = request.getItems().stream()
                .map(itemReq -> InvoiceLineItem.builder()
                        .productId(itemReq.getProductId())
                        .quantity(itemReq.getQuantity())
                        .unitPrice(itemReq.getUnitPrice())
                        .build())
                .collect(Collectors.toList());

        Invoice newInvoice = Invoice.builder()
                .invoiceNumber(request.getInvoiceNumber())
                .customerId(request.getCustomerId())
                .orderId(request.getOrderId())
                .currencyCode(request.getCurrencyCode() != null ? request.getCurrencyCode() : "VND")
                .issueDate(request.getIssueDate())
                .dueDate(request.getDueDate())
                .status(InvoiceStatus.DRAFT)
                .lineItems(items)
                .build();

        newInvoice.calculateTotalAmount();
        InvoiceJpaEntity savedEntity = jpaRepository.save(mapper.toJpaEntity(newInvoice));
        return mapToResponse(mapper.toDomainEntity(savedEntity));
    }

    @Transactional
    public InvoiceResponse generateInvoiceFromOrder(Long orderId) {
        OrderJpaEntity order = orderJpaRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy Đơn hàng với ID: " + orderId));

        Invoice newInvoice = Invoice.builder()
                .invoiceNumber("INV-" + System.currentTimeMillis())
                .customerId(order.getCustomerId())
                .orderId(order.getId())
                .currencyCode(order.getCurrencyCode())
                .exchangeRate(order.getExchangeRate())
                .issueDate(LocalDate.now())
                .dueDate(LocalDate.now().plusDays(15))
                .status(InvoiceStatus.DRAFT)
                .build();

        List<InvoiceLineItem> invoiceItems = order.getLineItems().stream()
                .map(orderItem -> InvoiceLineItem.builder()
                        .productId(orderItem.getProductId())
                        .quantity(orderItem.getQuantity())
                        .unitPrice(orderItem.getUnitPrice())
                        .build())
                .collect(Collectors.toList());

        newInvoice.setLineItems(invoiceItems);
        newInvoice.calculateTotalAmount();

        InvoiceJpaEntity savedEntity = jpaRepository.save(mapper.toJpaEntity(newInvoice));
        return mapToResponse(mapper.toDomainEntity(savedEntity));
    }

    @Transactional
    public void bulkAssign(BulkActionRequest request) {
        List<InvoiceJpaEntity> invoices = jpaRepository.findAllById(request.getInvoiceIds());
        invoices.forEach(inv -> inv.setAssignedTo(request.getAssignToUserId()));
        jpaRepository.saveAll(invoices);
    }

    @Transactional
    public void bulkDelete(BulkActionRequest request) {
        jpaRepository.deleteAllById(request.getInvoiceIds());
    }

    public byte[] exportPdf(Long invoiceId) {
        InvoiceJpaEntity invoice = jpaRepository.findById(invoiceId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy Hóa đơn với ID: " + invoiceId));

        if (invoice.getTemplateId() == null) {
            throw new RuntimeException("Hóa đơn này chưa được cấu hình Mẫu biểu (Template ID).");
        }

        DocumentTemplateJpaEntity template = templateRepository.findById(invoice.getTemplateId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy mẫu Hóa đơn."));

        Map<String, Object> data = new HashMap<>();
        data.put("invoice", invoice);
        data.put("lineItems", invoice.getLineItems());

        return pdfGenerationService.generatePdfFromHtmlString(template.getContentHtml(), data);
    }

    private InvoiceResponse mapToResponse(Invoice invoice) {
        List<InvoiceResponse.InvoiceLineItemResponse> itemResponses = invoice.getLineItems().stream()
                .map(item -> InvoiceResponse.InvoiceLineItemResponse.builder()
                        .id(item.getId())
                        .productId(item.getProductId())
                        .quantity(item.getQuantity())
                        .unitPrice(item.getUnitPrice())
                        .totalPrice(item.calculateTotalPrice())
                        .build())
                .collect(Collectors.toList());

        return InvoiceResponse.builder()
                .id(invoice.getId())
                .invoiceNumber(invoice.getInvoiceNumber())
                .customerId(invoice.getCustomerId())
                .orderId(invoice.getOrderId())
                .assignedTo(invoice.getAssignedTo())
                .totalAmount(invoice.getTotalAmount())
                .currencyCode(invoice.getCurrencyCode())
                .issueDate(invoice.getIssueDate())
                .dueDate(invoice.getDueDate())
                .status(invoice.getStatus())
                .createdAt(invoice.getCreatedAt())
                .lineItems(itemResponses)
                .build();
    }
}