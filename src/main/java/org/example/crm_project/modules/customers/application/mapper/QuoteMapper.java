package org.example.crm_project.modules.customers.application.mapper;

import org.example.crm_project.modules.customers.application.dto.request.CreateQuoteDTO;
import org.example.crm_project.modules.customers.application.dto.response.QuoteResponseDTO;
import org.example.crm_project.modules.customers.domain.entity.Quote;
import org.springframework.stereotype.Component;

/**
 * Mapper: QuoteMapper
 * DTO ↔ Domain entity conversion
 */
@Component
public class QuoteMapper {

    public Quote toEntity(CreateQuoteDTO createDTO) {
        if (createDTO == null) return null;

        Quote quote = new Quote();
        quote.setCustomerId(createDTO.getCustomerId());
        quote.setQuoteCode(createDTO.getQuoteCode());
        quote.setQuoteName(createDTO.getQuoteName());
        quote.setQuoteDate(createDTO.getQuoteDate());
        quote.setValidUntil(createDTO.getValidUntil());
        quote.setSubtotalAmount(createDTO.getSubtotalAmount());
        quote.setDiscountAmount(createDTO.getDiscountAmount());
        quote.setTotalAmount(createDTO.getTotalAmount());
        quote.setStatus(createDTO.getStatus());
        quote.setNotes(createDTO.getNotes());
        quote.setTemplateId(createDTO.getTemplateId());

        return quote;
    }

    public QuoteResponseDTO toResponseDTO(Quote quote) {
        if (quote == null) return null;

        QuoteResponseDTO dto = new QuoteResponseDTO();
        dto.setId(quote.getId());
        dto.setCustomerId(quote.getCustomerId());
        dto.setQuoteCode(quote.getQuoteCode());
        dto.setQuoteName(quote.getQuoteName());
        dto.setQuoteDate(quote.getQuoteDate());
        dto.setValidUntil(quote.getValidUntil());
        dto.setSubtotalAmount(quote.getSubtotalAmount());
        dto.setDiscountAmount(quote.getDiscountAmount());
        dto.setTotalAmount(quote.getTotalAmount());
        dto.setStatus(quote.getStatus());
        dto.setCreatedAt(quote.getCreatedAt());
        dto.setUpdatedAt(quote.getUpdatedAt());

        return dto;
    }

    public void updateEntityFromDTO(Quote quote, CreateQuoteDTO createDTO) {
        if (quote == null || createDTO == null) return;

        quote.setCustomerId(createDTO.getCustomerId());
        quote.setQuoteCode(createDTO.getQuoteCode());
        quote.setQuoteName(createDTO.getQuoteName());
        quote.setQuoteDate(createDTO.getQuoteDate());
        quote.setValidUntil(createDTO.getValidUntil());
        quote.setSubtotalAmount(createDTO.getSubtotalAmount());
        quote.setDiscountAmount(createDTO.getDiscountAmount());
        quote.setTotalAmount(createDTO.getTotalAmount());
        quote.setStatus(createDTO.getStatus());
        quote.setNotes(createDTO.getNotes());
        quote.setTemplateId(createDTO.getTemplateId());
    }
}
