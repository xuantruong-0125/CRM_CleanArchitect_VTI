package org.example.crm_project.modules.customers.domain.repository;

import org.example.crm_project.modules.customers.domain.entity.Contact;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Repository Interface: ContactRepository
 * Định nghĩa các phương thức tương tác với Contact trong Domain
 */
public interface ContactRepository {

    /**
     * Lưu hoặc cập nhật người liên hệ
     */
    Contact save(Contact contact);

    /**
     * Tìm người liên hệ theo ID
     */
    Optional<Contact> findById(Long id);

    /**
     * Lấy danh sách người liên hệ của khách hàng
     */
    List<Contact> findByCustomerId(Long customerId);

    /**
     * Lấy danh sách người liên hệ của khách hàng phân trang
     */
    Page<Contact> findByCustomerId(Long customerId, Pageable pageable);

    /**
     * Tìm người liên hệ chính của khách hàng
     */
    Optional<Contact> findPrimaryContact(Long customerId);

    /**
     * Tìm theo email
     */
    Optional<Contact> findByEmail(String email);

    /**
     * Tìm theo số điện thoại
     */
    List<Contact> findByPhone(String phone);

    /**
     * Xóa người liên hệ
     */
    void delete(Long id);

    /**
     * Xóa tất cả người liên hệ của khách hàng
     */
    void deleteByCustomerId(Long customerId);

    /**
     * Kiểm tra người liên hệ tồn tại
     */
    boolean existsById(Long id);

    /**
     * Lấy tổng số người liên hệ của khách hàng
     */
    long countByCustomerId(Long customerId);
}
