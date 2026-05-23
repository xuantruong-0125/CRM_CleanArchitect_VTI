package org.example.crm_project.modules.quote_management.infrastructure.persistence.repository;

import org.example.crm_project.modules.quote_management.infrastructure.persistence.entity.QuoteEntity;
import org.example.crm_project.modules.customers.infrastructure.persistence.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.Optional;

/**
 * Spring Data JPA repository cho QuoteEntity.
 * Tối ưu hóa index dựa trên:
 * - PRIMARY (id)
 * - uk_quote_number (quote_number)
 * - idx_customer_status (customer_id, status_id, deleted_at)
 * - idx_customer_created (customer_id, created_at, deleted_at)
 * - idx_active_created (deleted_at, created_at)
 */
@Repository
public interface JpaQuoteRepository extends JpaRepository<QuoteEntity, Integer> {

    // Lấy tất cả chưa bị xóa, sắp xếp mới nhất (Tận dụng idx_active_created)
    @Query("SELECT q FROM QuoteEntity q WHERE q.deletedAt IS NULL ORDER BY q.createdAt DESC")
    Page<QuoteEntity> findAllActive(Pageable pageable);

    // Lấy danh sách báo giá của 1 khách hàng (Tận dụng idx_customer_created)
    @Query("SELECT q FROM QuoteEntity q WHERE q.customerId = :customerId AND q.deletedAt IS NULL ORDER BY q.createdAt DESC")
    Page<QuoteEntity> findActiveByCustomerId(@Param("customerId") Long customerId, Pageable pageable);

    // Tìm theo id, chưa bị xóa
    @Query("SELECT q FROM QuoteEntity q WHERE q.id = :id AND q.deletedAt IS NULL")
    Optional<QuoteEntity> findActiveById(@Param("id") Integer id);

    // Tìm kiếm theo số báo giá hoặc tên khách hàng (JOIN sang customer)
    @Query("""
            SELECT q FROM QuoteEntity q
            LEFT JOIN CustomerEntity c ON c.id = q.customerId
            WHERE q.deletedAt IS NULL
            AND (q.quoteNumber LIKE CONCAT('%', :keyword, '%')
                 OR c.name LIKE CONCAT('%', :keyword, '%'))
            ORDER BY q.createdAt DESC
            """)
    Page<QuoteEntity> searchByKeyword(@Param("keyword") String keyword, Pageable pageable);

    /**
     * Tìm kiếm nâng cao chuẩn doanh nghiệp.
     */
    @Query("""
            SELECT q FROM QuoteEntity q
            LEFT JOIN CustomerEntity c ON c.id = q.customerId
            WHERE q.deletedAt IS NULL
            AND (:customerId IS NULL OR q.customerId = :customerId)
            AND (:statusId IS NULL OR q.statusId = :statusId)
            AND (:quoteNumber IS NULL OR q.quoteNumber LIKE CONCAT('%', :quoteNumber, '%'))
            AND (:fromDate IS NULL OR q.createdAt >= :fromDate)
            AND (:toDate IS NULL OR q.createdAt <= :toDate)
            AND (:keyword IS NULL OR (
                q.quoteNumber LIKE CONCAT('%', :keyword, '%') OR
                c.name LIKE CONCAT('%', :keyword, '%')
            ))
            """)
    Page<QuoteEntity> searchQuotes(
            @Param("keyword") String keyword,
            @Param("customerId") Long customerId,
            @Param("statusId") Integer statusId,
            @Param("quoteNumber") String quoteNumber,
            @Param("fromDate") java.time.LocalDateTime fromDate,
            @Param("toDate") java.time.LocalDateTime toDate,
            Pageable pageable);

    // Kiểm tra số báo giá đã tồn tại chưa
    boolean existsByQuoteNumberAndDeletedAtIsNull(String quoteNumber);

    // Kiểm tra số báo giá tồn tại (loại trừ id)
    @Query("SELECT COUNT(q) > 0 FROM QuoteEntity q WHERE q.quoteNumber = :quoteNumber AND q.id <> :excludeId AND q.deletedAt IS NULL")
    boolean existsByQuoteNumberAndIdNotAndDeletedAtIsNull(
            @Param("quoteNumber") String quoteNumber,
            @Param("excludeId") Integer excludeId);

    // Đếm số lượng báo giá theo khách hàng
    long countByCustomerIdAndDeletedAtIsNull(Long customerId);

    // Cập nhật trực tiếp total_amount – bypasses Hibernate entity cache
    @Modifying
    @Query("UPDATE QuoteEntity q SET q.totalAmount = :totalAmount WHERE q.id = :id")
    int updateTotalAmount(@Param("id") Integer id, @Param("totalAmount") java.math.BigDecimal totalAmount);

    // Cập nhật quote_number sau khi có ID (dùng khi auto-generate BG-{id})
    @Modifying
    @Query("UPDATE QuoteEntity q SET q.quoteNumber = :quoteNumber WHERE q.id = :id")
    int updateQuoteNumber(@Param("id") Integer id, @Param("quoteNumber") String quoteNumber);
}