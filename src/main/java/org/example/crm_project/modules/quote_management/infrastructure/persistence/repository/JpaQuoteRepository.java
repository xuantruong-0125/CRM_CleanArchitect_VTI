package org.example.crm_project.modules.quote_management.infrastructure.persistence.repository;

import org.example.crm_project.modules.quote_management.infrastructure.persistence.entity.QuoteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository cho QuoteEntity.
 * Chỉ dùng nội bộ trong infrastructure layer.
 */
@Repository
public interface JpaQuoteRepository extends JpaRepository<QuoteEntity, Integer> {

    // Lấy tất cả chưa bị xóa, sắp xếp mới nhất
    @Query("SELECT q FROM QuoteEntity q WHERE q.deletedAt IS NULL ORDER BY q.createdAt DESC")
    List<QuoteEntity> findAllActive();

    // Tìm theo id, chưa bị xóa
    @Query("SELECT q FROM QuoteEntity q WHERE q.id = :id AND q.deletedAt IS NULL")
    Optional<QuoteEntity> findActiveById(@Param("id") Integer id);

    // Tìm kiếm theo số báo giá hoặc tên khách hàng (JOIN sang customer)
    @Query("""
            SELECT q FROM QuoteEntity q
            LEFT JOIN CustomerEntity c ON c.id = q.customerId
            WHERE q.deletedAt IS NULL
            AND (LOWER(q.quoteNumber) LIKE LOWER(CONCAT('%', :keyword, '%'))
                 OR LOWER(c.name) LIKE LOWER(CONCAT('%', :keyword, '%')))
            ORDER BY q.createdAt DESC
            """)
    List<QuoteEntity> searchByKeyword(@Param("keyword") String keyword);

    // Kiểm tra số báo giá đã tồn tại chưa
    boolean existsByQuoteNumberAndDeletedAtIsNull(String quoteNumber);

    // Kiểm tra số báo giá tồn tại (loại trừ id)
    @Query("SELECT COUNT(q) > 0 FROM QuoteEntity q WHERE LOWER(q.quoteNumber) = LOWER(:quoteNumber) AND q.id <> :excludeId AND q.deletedAt IS NULL")
    boolean existsByQuoteNumberAndIdNotAndDeletedAtIsNull(
            @Param("quoteNumber") String quoteNumber,
            @Param("excludeId") Integer excludeId
    );

    // Đếm số lượng báo giá theo khách hàng
    long countByCustomerIdAndDeletedAtIsNull(Integer customerId);
}
