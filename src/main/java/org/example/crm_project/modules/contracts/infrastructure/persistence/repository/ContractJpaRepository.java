package org.example.crm_project.modules.contracts.infrastructure.persistence.repository;

import org.example.crm_project.modules.contracts.infrastructure.persistence.entity.ContractJpaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ContractJpaRepository extends JpaRepository<ContractJpaEntity, Long> {

    Optional<ContractJpaEntity> findByContractNumber(String contractNumber);

    boolean existsByContractNumber(String contractNumber);

    long countByOwnerId(Long ownerId);

    List<ContractJpaEntity> findAllByIdIn(List<Long> ids);

    // ─── Query JOIN customerName + ownerName ─────────────────────────────────

    /**
     * Native query JOIN sang customers và users để lấy tên hiển thị.
     * countQuery riêng biệt để tránh lỗi pagination với native query.
     */
    @Query(
            value = """
            SELECT
                c.id              AS id,
                c.contract_number AS contractNumber,
                c.customer_id     AS customerId,
                cust.name         AS customerName,
                c.quote_id        AS quoteId,
                c.template_id     AS templateId,
                c.contract_value  AS contractValue,
                c.currency_code   AS currencyCode,
                c.exchange_rate   AS exchangeRate,
                c.status          AS status,
                c.start_date      AS startDate,
                c.end_date        AS endDate,
                c.owner_id        AS ownerId,
                u.full_name       AS ownerName,
                c.created_at      AS createdAt,
                c.updated_at      AS updatedAt
            FROM contracts c
            LEFT JOIN customers cust ON cust.id = c.customer_id
                                    AND cust.deleted_at IS NULL
            LEFT JOIN users u        ON u.id    = c.owner_id
            WHERE c.deleted_at IS NULL
              AND (:keyword    IS NULL OR c.contract_number LIKE CONCAT('%', :keyword, '%')
                                      OR cust.name          LIKE CONCAT('%', :keyword, '%'))
              AND (:status     IS NULL OR c.status      = :status)
              AND (:ownerId    IS NULL OR c.owner_id    = :ownerId)
              AND (:customerId IS NULL OR c.customer_id = :customerId)
              AND (:startDateFrom IS NULL OR c.start_date    >= :startDateFrom)
              AND (:startDateTo   IS NULL OR c.start_date    <= :startDateTo)
              AND (:endDateFrom   IS NULL OR c.end_date      >= :endDateFrom)
              AND (:endDateTo     IS NULL OR c.end_date      <= :endDateTo)
              AND (:valueFrom     IS NULL OR c.contract_value >= :valueFrom)
              AND (:valueTo       IS NULL OR c.contract_value <= :valueTo)
            ORDER BY c.created_at DESC
            """,
            countQuery = """
            SELECT COUNT(c.id)
            FROM contracts c
            LEFT JOIN customers cust ON cust.id = c.customer_id AND cust.deleted_at IS NULL
            WHERE c.deleted_at IS NULL
              AND (:keyword    IS NULL OR c.contract_number LIKE CONCAT('%', :keyword, '%')
                                      OR cust.name          LIKE CONCAT('%', :keyword, '%'))
              AND (:status     IS NULL OR c.status      = :status)
              AND (:ownerId    IS NULL OR c.owner_id    = :ownerId)
              AND (:customerId IS NULL OR c.customer_id = :customerId)
              AND (:startDateFrom IS NULL OR c.start_date    >= :startDateFrom)
              AND (:startDateTo   IS NULL OR c.start_date    <= :startDateTo)
              AND (:endDateFrom   IS NULL OR c.end_date      >= :endDateFrom)
              AND (:endDateTo     IS NULL OR c.end_date      <= :endDateTo)
              AND (:valueFrom     IS NULL OR c.contract_value >= :valueFrom)
              AND (:valueTo       IS NULL OR c.contract_value <= :valueTo)
            """,
            nativeQuery = true
    )
    Page<ContractSummaryProjection> searchContractsWithNames(
            @Param("keyword")       String keyword,
            @Param("status")        String status,
            @Param("ownerId")       Long ownerId,
            @Param("customerId")    Long customerId,
            @Param("startDateFrom") LocalDate startDateFrom,
            @Param("startDateTo")   LocalDate startDateTo,
            @Param("endDateFrom")   LocalDate endDateFrom,
            @Param("endDateTo")     LocalDate endDateTo,
            @Param("valueFrom")     java.math.BigDecimal valueFrom,
            @Param("valueTo")       java.math.BigDecimal valueTo,
            Pageable pageable
    );

    @Modifying
    @Query("UPDATE ContractJpaEntity c SET c.deletedAt = CURRENT_TIMESTAMP, c.updatedBy = :deletedBy WHERE c.id = :id")
    void softDeleteById(@Param("id") Long id, @Param("deletedBy") Long deletedBy);

    /**
     * Tìm số thứ tự lớn nhất của tháng hiện tại để sinh số HĐ tiếp theo.
     * VD: prefix = "HD-202604-" → trả về MAX phần số sau prefix.
     */
    @Query(value = """
            SELECT COALESCE(
                MAX(CAST(SUBSTRING(contract_number, :prefixLen + 1) AS UNSIGNED)),
                0
            )
            FROM contracts
            WHERE contract_number LIKE CONCAT(:prefix, '%')
              AND deleted_at IS NULL
            """, nativeQuery = true)
    int findMaxSequenceByPrefix(@Param("prefix") String prefix,
                                @Param("prefixLen") int prefixLen);
}