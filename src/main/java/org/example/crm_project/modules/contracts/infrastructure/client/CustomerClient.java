package org.example.crm_project.modules.contracts.infrastructure.client;

/**
 * Anti-Corruption Layer: truy vấn thông tin khách hàng từ module Customer.
 * Trong monolith có thể gọi trực tiếp CustomerRepository;
 * trong microservice thì gọi REST/Feign.
 */
public interface CustomerClient {
    String getCustomerName(Long customerId);
}
