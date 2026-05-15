package org.example.crm_project.modules.customers.domain.repository;

import org.example.crm_project.modules.customers.domain.entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Repository Interface: TaskRepository
 * Định nghĩa các phương thức tương tác với Task trong Domain
 */
public interface TaskRepository {

    /**
     * Lưu hoặc cập nhật công việc
     */
    Task save(Task task);

    /**
     * Tìm công việc theo ID
     */
    Optional<Task> findById(Long id);

    /**
     * Lấy danh sách công việc được gán cho người dùng
     */
    Page<Task> findByAssignedTo(Long userId, Pageable pageable);

    /**
     * Lấy danh sách công việc theo khách hàng
     */
    Page<Task> findByCustomerId(Long customerId, Pageable pageable);

    /**
     * Lấy danh sách công việc theo trạng thái
     */
    Page<Task> findByStatus(String status, Pageable pageable);

    /**
     * Lấy danh sách công việc theo độ ưu tiên
     */
    Page<Task> findByPriority(String priority, Pageable pageable);

    /**
     * Lấy danh sách công việc theo loại và ID
     */
    List<Task> findByRelatedToTypeAndRelatedToId(String relatedToType, Long relatedToId);

    /**
     * Lấy danh sách công việc quá hạn
     */
    List<Task> findOverdueTasks(LocalDate currentDate);

    /**
     * Lấy danh sách công việc sắp đến hạn
     */
    List<Task> findUpcomingTasks(LocalDate startDate, LocalDate endDate);

    /**
     * Xóa công việc (soft delete)
     */
    void delete(Long id);

    /**
     * Kiểm tra công việc tồn tại
     */
    boolean existsById(Long id);

    /**
     * Lấy tổng số công việc
     */
    long count();
}
