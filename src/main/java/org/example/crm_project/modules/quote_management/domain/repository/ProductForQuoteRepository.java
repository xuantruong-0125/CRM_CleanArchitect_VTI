package org.example.crm_project.modules.quote_management.domain.repository;

import org.example.crm_project.modules.quote_management.application.dto.response.QuoteFormProductResponse;

import java.util.List;

public interface ProductForQuoteRepository {
    List<QuoteFormProductResponse> findAllActiveForForm();
}
