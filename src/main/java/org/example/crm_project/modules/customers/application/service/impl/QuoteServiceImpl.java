package org.example.crm_project.modules.customers.application.service.impl;

import org.example.crm_project.modules.customers.application.dto.request.CreateQuoteDTO;
import org.example.crm_project.modules.customers.application.dto.response.QuoteResponseDTO;
import org.example.crm_project.modules.customers.application.mapper.QuoteMapper;
import org.example.crm_project.modules.customers.application.service.QuoteService;
import org.example.crm_project.modules.customers.domain.entity.Quote;
import org.example.crm_project.modules.customers.domain.repository.QuoteRepository;
import org.example.crm_project.modules.customers.domain.exception.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation: QuoteServiceImpl
 * Business logic for Quote management
 */
@Service
@Transactional
public class QuoteServiceImpl implements QuoteService {

    private final QuoteRepository quoteRepository;
    private final QuoteMapper quoteMapper;

    public QuoteServiceImpl(QuoteRepository quoteRepository, QuoteMapper quoteMapper) {
        this.quoteRepository = quoteRepository;
        this.quoteMapper = quoteMapper;
    }

    @Override
    public QuoteResponseDTO createQuote(CreateQuoteDTO createDTO) {
        Quote quote = quoteMapper.toEntity(createDTO);
        Quote saved = quoteRepository.save(quote);
        return quoteMapper.toResponseDTO(saved);
    }

    @Override
    public QuoteResponseDTO getQuoteById(Long id) {
        Quote quote = quoteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Báo giá không tìm thấy: " + id));
        return quoteMapper.toResponseDTO(quote);
    }

    @Override
    public Optional<QuoteResponseDTO> getQuoteByCode(String quoteCode) {
        return quoteRepository.findByQuoteCode(quoteCode)
                .map(quoteMapper::toResponseDTO);
    }

    @Override
    public Page<QuoteResponseDTO> getQuotesByCustomer(Long customerId, Pageable pageable) {
        Page<Quote> quotes = quoteRepository.findByCustomerId(customerId, pageable);
        return quotes.map(quoteMapper::toResponseDTO);
    }

    @Override
    public Page<QuoteResponseDTO> getQuotesByStatus(String status, Pageable pageable) {
        Page<Quote> quotes = quoteRepository.findByStatus(status, pageable);
        return quotes.map(quoteMapper::toResponseDTO);
    }

    @Override
    public QuoteResponseDTO updateQuote(Long id, CreateQuoteDTO createDTO) {
        Quote quote = quoteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Báo giá không tìm thấy: " + id));
        quoteMapper.updateEntityFromDTO(quote, createDTO);
        Quote updated = quoteRepository.save(quote);
        return quoteMapper.toResponseDTO(updated);
    }

    @Override
    public void deleteQuote(Long id) {
        if (!quoteRepository.existsById(id)) {
            throw new EntityNotFoundException("Báo giá không tìm thấy: " + id);
        }
        quoteRepository.delete(id);
    }

    @Override
    @Transactional(readOnly = true)
    public long countQuotes() {
        return quoteRepository.count();
    }
}
