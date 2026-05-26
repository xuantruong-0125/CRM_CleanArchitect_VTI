package org.example.crm_project.modules.task_managerment.domain.entity;

import java.time.LocalDateTime;

import org.example.crm_project.modules.task_managerment.domain.constant.TaskPriority;
import org.example.crm_project.modules.task_managerment.domain.constant.TaskStatus;

import lombok.Setter;

@Setter
public class Task {
    private Long id;
    private String subject;
    private String description;
    private LocalDateTime startDate;
    private LocalDateTime dueDate;
    private LocalDateTime completedAt;
    private TaskStatus status;
    private TaskPriority priority;
    private Integer progressPercent;

    // Các thông tin liên kết (Chỉ đọc, khóa chết sau khi tạo)
    private String relatedToType;
    private Long relatedToId;
    private Long assignedTo;
    private Long assignedBy;
    private Long contactId;

    // Audit log
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private Long organizationId;

    // No-arg constructor (cần thiết để Jackson deserialize JSON)
    public Task() {
    }

    public Task(Long id, String subject, String description, LocalDateTime startDate, LocalDateTime dueDate,
            LocalDateTime completedAt, TaskStatus status, TaskPriority priority, Integer progressPercent,
            String relatedToType, Long relatedToId, Long assignedTo, Long assignedBy, Long organizationId,
            Long contactId,
            LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.subject = subject;
        this.description = description;
        this.startDate = startDate;
        this.dueDate = dueDate;
        this.completedAt = completedAt;
        this.status = status;
        this.priority = priority;
        this.progressPercent = progressPercent;
        this.relatedToType = relatedToType;
        this.relatedToId = relatedToId;
        this.assignedTo = assignedTo;
        this.assignedBy = assignedBy;
        this.organizationId = organizationId;
        this.contactId = contactId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }

    public String getSubject() {
        return subject;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public TaskPriority getPriority() {
        return priority;
    }

    public Integer getProgressPercent() {
        return progressPercent;
    }

    public String getRelatedToType() {
        return relatedToType;
    }

    public Long getRelatedToId() {
        return relatedToId;
    }

    public Long getAssignedTo() {
        return assignedTo;
    }

    public Long getAssignedBy() {
        return assignedBy;
    }

    public Long getOrganizationId() {
        return organizationId;
    }

    public Long getContactId() {
        return contactId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    // ===== BUSINESS LOGIC: LUẬT NGHIỆP VỤ =====

    public void updateInfo(String subject, String description, LocalDateTime startDate,
            LocalDateTime dueDate, TaskStatus status, TaskPriority priority,
            Integer progressPercent) {

        validateSubject(subject);
        validateProgress(progressPercent);
        validateDates(startDate, dueDate);

        this.subject = subject;
        this.description = description;
        if (startDate != null)
            this.startDate = startDate;
        if (dueDate != null)
            this.dueDate = dueDate;
        if (priority != null)
            this.priority = priority;

        // Xử lý logic liên kết chặt chẽ giữa Trạng thái và Phần trăm tiến độ
        handleStatusAndProgressLogic(status, progressPercent);
    }

    // --- CÁC HÀM VALIDATE BẢO VỆ LÕI ---

    private void validateSubject(String subject) {
        if (subject == null || subject.trim().isEmpty()) {
            throw new IllegalArgumentException("Tiêu đề công việc không được để trống");
        }
    }

    private void validateProgress(Integer progress) {
        if (progress != null && (progress < 0 || progress > 100)) {
            throw new IllegalArgumentException("Phần trăm tiến độ phải nằm trong khoảng từ 0 đến 100");
        }
    }

    private void validateDates(LocalDateTime start, LocalDateTime due) {
        if (start != null && due != null && start.isAfter(due)) {
            throw new IllegalArgumentException("Ngày bắt đầu không được lớn hơn Hạn chót (Due Date)");
        }
    }

    // --- LOGIC ĐỒNG BỘ TRẠNG THÁI & TIẾN ĐỘ (CỰC KỲ QUAN TRỌNG) ---

    private void handleStatusAndProgressLogic(TaskStatus newStatus, Integer newProgress) {
        if (newStatus != null)
            this.status = newStatus;
        if (newProgress != null)
            this.progressPercent = newProgress;

        if ((this.status == TaskStatus.CANCELED || this.status == TaskStatus.DEFERRED)
                && this.progressPercent != null && this.progressPercent == 100) {
            throw new IllegalArgumentException("Không thể đặt tiến độ 100% cho công việc Đã hủy hoặc Tạm hoãn!");
        }

        if (this.progressPercent != null && this.progressPercent == 100) {
            this.status = TaskStatus.COMPLETED;
        }

        if (this.status == TaskStatus.COMPLETED) {
            this.progressPercent = 100;
            if (this.completedAt == null) {
                this.completedAt = LocalDateTime.now();
            }
        }

        else {
            this.completedAt = null;
        }

        if (this.status == TaskStatus.NOT_STARTED && this.progressPercent != null && this.progressPercent > 0) {
            this.status = TaskStatus.IN_PROGRESS;
        }
    }

}
