package org.example.crm_project.modules.customers.application.service;

import org.example.crm_project.modules.customers.application.dto.response.ContactResponseDTO;
import org.example.crm_project.modules.customers.application.dto.request.CreateContactDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface: ContactService
 */
public interface ContactService {

    /**
     * Tạo người liên hệ mới
     */
    ContactResponseDTO createContact(Long customerId, CreateContactDTO createDTO);

    /**
     * Cập nhật người liên hệ
     */
    ContactResponseDTO updateContact(Long contactId, CreateContactDTO updateDTO);

    /**
     * Lấy thông tin người liên hệ
     */
    ContactResponseDTO getContactById(Long contactId);

    /**
     * Lấy danh sách người liên hệ của khách hàng
     */
    List<ContactResponseDTO> getContactsByCustomer(Long customerId);

    /**
     * Lấy danh sách người liên hệ phân trang
     */
    Page<ContactResponseDTO> getContactsByCustomerPaginated(Long customerId, Pageable pageable);

    /**
     * Lấy người liên hệ chính
     */
    ContactResponseDTO getPrimaryContact(Long customerId);

    /**
     * Xóa người liên hệ
     */
    void deleteContact(Long contactId);

    /**
     * Xóa tất cả người liên hệ của khách hàng
     */
    void deleteAllContactsByCustomer(Long customerId);

    /**
     * Đặt người liên hệ làm chính
     */
    void setPrimaryContact(Long contactId);

    /**
     * Lấy tổng số người liên hệ
     */
    long getTotalContactsCount(Long customerId);
}
