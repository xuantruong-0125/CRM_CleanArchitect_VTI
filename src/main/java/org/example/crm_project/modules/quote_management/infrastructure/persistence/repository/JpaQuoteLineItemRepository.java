package org.example.crm_project.modules.quote_management.infrastructure.persistence.repository;

import org.example.crm_project.modules.quote_management.infrastructure.persistence.entity.QuoteLineItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data JPA repository cho QuoteLineItemEntity.
 */
@Repository
public interface JpaQuoteLineItemRepository extends JpaRepository<QuoteLineItemEntity, Integer> {

    // Lấy tất cả line items của một quote
    @Query("SELECT qli FROM QuoteLineItemEntity qli WHERE qli.quoteId = :quoteId")
    List<QuoteLineItemEntity> findByQuoteId(@Param("quoteId") Integer quoteId);

    // Xóa tất cả line items của một quote
    @Modifying
    @Query("DELETE FROM QuoteLineItemEntity qli WHERE qli.quoteId = :quoteId")
    void deleteByQuoteId(@Param("quoteId") Integer quoteId);
}
