package org.example.crm_project.modules.contracts.infrastructure.persistence.repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Projection: kết quả native query JOIN contracts + customers + users.
 * Dùng interface-based projection của Spring Data JPA.
 */
public interface ContractSummaryProjection {

    Long       getId();
    String     getContractNumber();
    Long       getCustomerId();
    String     getCustomerName();      // ← từ customers.name
    Long       getQuoteId();
    Long       getTemplateId();
    BigDecimal getContractValue();
    String     getCurrencyCode();
    BigDecimal getExchangeRate();
    String     getStatus();            // String vì native query
    LocalDate  getStartDate();
    LocalDate  getEndDate();
    Long       getOwnerId();
    String     getOwnerName();         // ← từ users.full_name
    LocalDateTime getCreatedAt();
    LocalDateTime getUpdatedAt();
}