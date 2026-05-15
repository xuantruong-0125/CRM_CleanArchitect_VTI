package org.example.crm_project.modules.customers.application.service;

import org.example.crm_project.modules.customers.application.dto.request.CreateQuoteDTO;
import org.example.crm_project.modules.customers.application.dto.response.QuoteResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface: QuoteService
 * Defines business operations for Quote management
 */
public interface QuoteService {

    /**
     * Create a new quote
     *
     * @param createDTO the quote data
     * @return the created quote
     */
    QuoteResponseDTO createQuote(CreateQuoteDTO createDTO);

    /**
     * Get quote by ID
     *
     * @param id the quote ID
     * @return the quote response DTO
     */
    QuoteResponseDTO getQuoteById(Long id);

    /**
     * Get quote by code
     *
     * @param quoteCode the quote code
     * @return the quote response DTO if found
     */
    Optional<QuoteResponseDTO> getQuoteByCode(String quoteCode);

    /**
     * Get all quotes by customer with pagination
     *
     * @param customerId the customer ID
     * @param pageable pagination information
     * @return paginated list of quotes
     */
    Page<QuoteResponseDTO> getQuotesByCustomer(Long customerId, Pageable pageable);

    /**
     * Get all quotes by status with pagination
     *
     * @param status the quote status
     * @param pageable pagination information
     * @return paginated list of quotes
     */
    Page<QuoteResponseDTO> getQuotesByStatus(String status, Pageable pageable);

    /**
     * Update an existing quote
     *
     * @param id the quote ID
     * @param createDTO the updated quote data
     * @return the updated quote response DTO
     */
    QuoteResponseDTO updateQuote(Long id, CreateQuoteDTO createDTO);

    /**
     * Delete a quote (soft delete)
     *
     * @param id the quote ID
     */
    void deleteQuote(Long id);

    /**
     * Count total quotes
     *
     * @return total number of quotes
     */
    long countQuotes();
}
