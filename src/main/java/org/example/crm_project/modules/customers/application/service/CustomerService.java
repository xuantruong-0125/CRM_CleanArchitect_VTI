package org.example.crm_project.modules.customers.application.service;

import org.example.crm_project.modules.customers.application.dto.request.CreateCustomerDTO;
import org.example.crm_project.modules.customers.application.dto.response.CustomerResponseDTO;
import org.example.crm_project.modules.customers.application.dto.request.UpdateCustomerDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface: CustomerService
 * Định nghĩa các use case của Customer
 */
public interface CustomerService {

    /**
     * Tạo khách hàng mới
     */
    CustomerResponseDTO createCustomer(CreateCustomerDTO createDTO);

    /**
     * Cập nhật khách hàng
     */
    CustomerResponseDTO updateCustomer(Long customerId, UpdateCustomerDTO updateDTO);

    /**
     * Lấy thông tin khách hàng
     */
    CustomerResponseDTO getCustomerById(Long customerId);

    /**
     * Lấy danh sách khách hàng phân trang
     */
    Page<CustomerResponseDTO> getAllCustomers(Pageable pageable);

    /**
     * Lấy danh sách khách hàng B2B
     */
    Page<CustomerResponseDTO> getB2BCustomers(Pageable pageable);

    /**
     * Lấy danh sách khách hàng B2C
     */
    Page<CustomerResponseDTO> getB2CCustomers(Pageable pageable);

    /**
     * Tìm khách hàng theo email
     */
    CustomerResponseDTO findByEmail(String email);

    /**
     * Tìm khách hàng theo mã khách hàng
     */
    CustomerResponseDTO findByCustomerCode(String customerCode);

    /**
     * Lấy danh sách khách hàng theo người phụ trách
     */
    Page<CustomerResponseDTO> getCustomersByAssignedUser(Long userId, Pageable pageable);

    /**
     * Lấy danh sách khách hàng theo trạng thái
     */
    Page<CustomerResponseDTO> getCustomersByStatus(Long statusId, Pageable pageable);

    /**
     * Lấy danh sách khách hàng theo tier
     */
    Page<CustomerResponseDTO> getCustomersByTier(Long tierId, Pageable pageable);

    /**
     * Xóa khách hàng (soft delete)
     */
    void deleteCustomer(Long customerId);

    /**
     * Cập nhật trạng thái khách hàng
     */
    void updateCustomerStatus(Long customerId, Long statusId);

    /**
     * Cập nhật tier khách hàng
     */
    void updateCustomerTier(Long customerId, Long tierId);

    /**
     * Gán khách hàng cho nhân viên
     */
    void assignCustomerToUser(Long customerId, Long userId);

    /**
     * Lấy tổng số khách hàng
     */
    long getTotalCustomersCount();
}
