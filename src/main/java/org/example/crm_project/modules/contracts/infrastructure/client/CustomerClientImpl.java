package org.example.crm_project.modules.contracts.infrastructure.client;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomerClientImpl implements CustomerClient {

    private final EntityManager entityManager;

    @Override
    public String getCustomerName(Long customerId) {
        try {
            Object result = entityManager
                    .createNativeQuery(
                            "SELECT name FROM customers WHERE id = :id AND deleted_at IS NULL")
                    .setParameter("id", customerId)
                    .getSingleResult();
            return result != null ? result.toString() : "Khách hàng #" + customerId;
        } catch (Exception e) {
            log.warn("Không tìm thấy khách hàng ID={}", customerId);
            return "Khách hàng #" + customerId;
        }
    }
}