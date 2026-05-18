package org.example.crm_project.modules.contracts.infrastructure.client;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Slf4j
@Component
@RequiredArgsConstructor
public class QuoteClientImpl implements QuoteClient {

    private final EntityManager entityManager;

    @Override
    public QuoteData getQuoteById(Long quoteId) {
        try {
            // Lưu ý: bảng quotes dùng status_id (FK) thay vì status string.
            // Không query cột status — chỉ lấy dữ liệu tài chính cần thiết.
            Object[] row = (Object[]) entityManager
                    .createNativeQuery("""
                        SELECT id, customer_id, total_amount,
                               currency_code, exchange_rate, status_id
                        FROM quotes
                        WHERE id = ?1 AND deleted_at IS NULL
                    """)
                    .setParameter(1, quoteId)
                    .getSingleResult();

            QuoteData data = new QuoteData();
            data.setId(((Number) row[0]).longValue());
            data.setCustomerId(row[1] != null ? ((Number) row[1]).longValue() : null);
            data.setTotalAmount(row[2] != null ? new BigDecimal(row[2].toString()) : BigDecimal.ZERO);
            data.setCurrencyCode(row[3] != null ? row[3].toString() : "VND");
            data.setExchangeRate(row[4] != null ? new BigDecimal(row[4].toString()) : BigDecimal.ONE);
            // status_id != null → coi là đã có trạng thái (đã xử lý)
            data.setStatus(row[5] != null ? "APPROVED" : "DRAFT");
            return data;

        } catch (NoResultException e) {
            throw new IllegalArgumentException("Không tìm thấy báo giá với ID: " + quoteId);
        } catch (Exception e) {
            log.error("Lỗi truy vấn báo giá ID={}: {}", quoteId, e.getMessage(), e);
            throw new IllegalArgumentException("Không thể đọc thông tin báo giá ID: " + quoteId
                    + " — " + e.getMessage());
        }
    }
}