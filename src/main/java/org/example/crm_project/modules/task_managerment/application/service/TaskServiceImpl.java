package org.example.crm_project.modules.task_managerment.application.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.example.crm_project.modules.system_managerment.application.service.UserService;
import org.example.crm_project.modules.task_managerment.application.dto.request.CreateTaskRequest;
import org.example.crm_project.modules.task_managerment.application.dto.request.UpdateTaskRequest;
import org.example.crm_project.modules.task_managerment.application.dto.response.TaskHistoryResponse;
import org.example.crm_project.modules.task_managerment.application.dto.response.TaskResponse;
import org.example.crm_project.modules.task_managerment.application.mapper.TaskMapper;
import org.example.crm_project.modules.task_managerment.domain.constant.TaskPriority;
import org.example.crm_project.modules.task_managerment.domain.constant.TaskStatus;
import org.example.crm_project.modules.task_managerment.domain.entity.Task;
import org.example.crm_project.modules.task_managerment.domain.event.TaskUpdatedEvent;
import org.example.crm_project.modules.task_managerment.domain.repository.TaskRepository;
import org.example.crm_project.modules.task_managerment.infrastructure.persistence.entity.TaskHistoryEntity;
import org.example.crm_project.modules.task_managerment.infrastructure.persistence.entity.TaskJpaEntity;
import org.example.crm_project.modules.task_managerment.infrastructure.persistence.mapper.TaskJpaMapper;
import org.example.crm_project.modules.task_managerment.infrastructure.persistence.repository.JpaTaskHistoryRepository;
import org.example.crm_project.modules.task_managerment.infrastructure.persistence.repository.JpaTaskRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final UserService userService;
    private final TaskRepository taskRepository;

    private final ApplicationEventPublisher eventPublisher;
    private final JpaTaskHistoryRepository historyRepository;

    @Override
    public Page<TaskResponse> getAllTasks(String subject, String status, String priority, int page, int size) {
        // Tạo đối tượng yêu cầu phân trang của Spring
        Pageable pageable = PageRequest.of(page, size);

        // Chuyển đổi String từ Frontend thành Enum của Backend (Nếu có truyền)
        TaskStatus taskStatus = (status != null && !status.isEmpty()) ? TaskStatus.valueOf(status) : null;
        TaskPriority taskPriority = (priority != null && !priority.isEmpty()) ? TaskPriority.valueOf(priority) : null;

        // Lên Database lấy dữ liệu (Nó sẽ tự động trả về cục Page chứa Entity)
        Page<Task> domainPage = taskRepository.searchTasks(subject, taskStatus, taskPriority, pageable);

        // 2. Map từ Domain -> Response
        return domainPage.map(domain -> {

            // Lấy FullName từ UserService
            String fullName = null;
            if (domain.getAssignedTo() != null) {
                try {
                    var userDto = userService.getById(domain.getAssignedTo());
                    fullName = userDto.getFullName();
                } catch (Exception e) {
                    fullName = "User không tồn tại";
                }
            }

            // domain lúc này đã là class Task rồi, ném thẳng vào Mapper luôn
            return TaskMapper.toResponse(domain, fullName);
        });
    }

    @Override
    public TaskResponse createTask(CreateTaskRequest request, Long assignedByUserId) {

        // 1. Khởi tạo đối tượng Task (Domain) với dữ liệu từ Request
        Task newTask = new Task(
                null, // ID tự tăng, để null
                request.getSubject(),
                request.getDescription(),
                request.getStartDate(),
                request.getDueDate(),
                null,
                TaskStatus.NOT_STARTED,

                request.getPriority() != null ? request.getPriority() : TaskPriority.NORMAL,

                0,
                request.getRelatedToType(),
                request.getRelatedToId(),
                request.getAssignedTo(),
                assignedByUserId, // Người giao việc
                request.getContactId(),
                LocalDateTime.now(), // createdAt
                LocalDateTime.now() // updatedAt
        );

        // 2. Gọi Repository lưu xuống Database
        Task savedTask = taskRepository.save(newTask);

        // 3. Lấy tên người được giao việc để trả về cho Frontend
        String assigneeName = null;
        if (savedTask.getAssignedTo() != null) {
            try {
                var userDto = userService.getById(savedTask.getAssignedTo());
                assigneeName = userDto.getFullName();
            } catch (Exception e) {
                assigneeName = "User không tồn tại";
            }
        }

        // 4. Map từ Domain -> Response và trả về
        return TaskMapper.toResponse(savedTask, assigneeName);
    }

    @Override
    @Transactional
    public TaskResponse updateTask(Long id, UpdateTaskRequest request) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy Task với ID: " + id));

        TaskStatus oldStatus = task.getStatus();
        Integer oldProgress = task.getProgressPercent();

        String oldSubject = task.getSubject();

        Long oldAssigneeId = task.getAssignedTo();
        Long oldContactId = task.getContactId();

        TaskPriority oldPriority = task.getPriority();

        LocalDateTime oldStartDate = task.getStartDate();
        LocalDateTime oldDueDate = task.getDueDate();
        String oldDescription = task.getDescription();

        // 2. Nhận dữ liệu từ Request đắp vào Entity
        if (request.getProgressPercent() != null)
            task.setProgressPercent(request.getProgressPercent());
        if (request.getStatus() != null)
            task.setStatus(request.getStatus());
        if (request.getSubject() != null)
            task.setSubject(request.getSubject());
        if (request.getDescription() != null)
            task.setDescription(request.getDescription());
        if (request.getPriority() != null)
            task.setPriority(request.getPriority());
        if (request.getStartDate() != null)
            task.setStartDate(request.getStartDate());
        if (request.getDueDate() != null)
            task.setDueDate(request.getDueDate());
        if (request.getAssigneeId() != null) {
            task.setAssignedTo(request.getAssigneeId());
        }
        if (request.getContactId() != null) {
            task.setContactId(request.getContactId());
        }

        if (task.getProgressPercent() != null && task.getProgressPercent() == 100) {
            task.setStatus(TaskStatus.COMPLETED);
        } else if (task.getStatus() == TaskStatus.COMPLETED) {
            task.setProgressPercent(100);
        }

        if (task.getStatus() == TaskStatus.COMPLETED) {
            if (oldStatus != TaskStatus.COMPLETED) {
                task.setCompletedAt(LocalDateTime.now());
            }
        } else {
            task.setCompletedAt(null);
        }

        task.setUpdatedAt(LocalDateTime.now());

        // 5. Lưu xuống Database
        Task savedTask = taskRepository.save(task);

        Long currentUserId = 1L;

        if (oldStatus != savedTask.getStatus()) {
            eventPublisher.publishEvent(new TaskUpdatedEvent(
                    savedTask.getId(), currentUserId, "status",
                    oldStatus != null ? oldStatus.name() : "",
                    savedTask.getStatus().name()));
        }

        if (!java.util.Objects.equals(oldProgress, savedTask.getProgressPercent())) {
            eventPublisher.publishEvent(new TaskUpdatedEvent(
                    savedTask.getId(), currentUserId, "progressPercent",
                    oldProgress != null ? String.valueOf(oldProgress) : "0",
                    String.valueOf(savedTask.getProgressPercent())));
        }

        if (!java.util.Objects.equals(oldSubject, savedTask.getSubject())) {
            eventPublisher.publishEvent(new TaskUpdatedEvent(
                    savedTask.getId(), currentUserId, "subject",
                    oldSubject != null ? oldSubject : "Chưa có chủ đề",
                    savedTask.getSubject() != null ? savedTask.getSubject() : ""));
        }

        if (!java.util.Objects.equals(oldDescription, savedTask.getDescription())) {
            eventPublisher.publishEvent(new TaskUpdatedEvent(
                    savedTask.getId(), currentUserId, "description",
                    oldDescription != null ? oldDescription : "Chưa có mô tả",
                    savedTask.getDescription() != null ? savedTask.getDescription() : "Đã xóa mô tả"));
        }

        if (!java.util.Objects.equals(oldPriority, savedTask.getPriority())) {
            eventPublisher.publishEvent(new TaskUpdatedEvent(
                    savedTask.getId(), currentUserId, "priority",
                    oldPriority != null ? oldPriority.toString() : "Chưa xét",
                    savedTask.getPriority() != null ? savedTask.getPriority().toString() : ""));
        }

        if (!java.util.Objects.equals(oldAssigneeId, savedTask.getAssignedTo())) {
            eventPublisher.publishEvent(new TaskUpdatedEvent(
                    savedTask.getId(), currentUserId, "assigneeId",
                    oldAssigneeId != null ? String.valueOf(oldAssigneeId) : "Chưa phân công",
                    savedTask.getAssignedTo() != null ? String.valueOf(savedTask.getAssignedTo()) : "Chưa phân công"));
        }

        if (!java.util.Objects.equals(oldStartDate, savedTask.getStartDate())) {
            eventPublisher.publishEvent(new TaskUpdatedEvent(
                    savedTask.getId(), currentUserId, "startDate",
                    oldStartDate != null ? oldStartDate.toString() : "Chưa có",
                    savedTask.getStartDate() != null ? savedTask.getStartDate().toString() : "Đã xóa"));
        }

        if (!java.util.Objects.equals(oldDueDate, savedTask.getDueDate())) {
            eventPublisher.publishEvent(new TaskUpdatedEvent(
                    savedTask.getId(), currentUserId, "dueDate",
                    oldDueDate != null ? oldDueDate.toString() : "Chưa có",
                    savedTask.getDueDate() != null ? savedTask.getDueDate().toString() : "Đã xóa"));
        }
        if (!java.util.Objects.equals(oldContactId, savedTask.getContactId())) {
            eventPublisher.publishEvent(new TaskUpdatedEvent(
                    savedTask.getId(), currentUserId, "contactId",
                    oldContactId != null ? String.valueOf(oldContactId) : "Chưa gắn liên hệ",
                    savedTask.getContactId() != null ? String.valueOf(savedTask.getContactId()) : "Đã gỡ liên hệ"));
        }

        // Lấy lại tên người thực hiện để trả về
        String assigneeName = null;
        if (savedTask.getAssignedTo() != null) {
            try {
                var userDto = userService.getById(savedTask.getAssignedTo());
                assigneeName = userDto.getFullName();
            } catch (Exception e) {
                assigneeName = "User không tồn tại";
            }
        }

        // 5. Trả về Frontend
        return TaskMapper.toResponse(savedTask, assigneeName);
    }

    @Override
    public TaskResponse getTaskById(Long id) {
        // 1. Lấy Domain Entity từ Repository (Domain interface)
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy Task với ID: " + id));

        // 2. Lấy FullName người thực hiện
        String fullName = null;
        if (task.getAssignedTo() != null) {
            try {
                var userDto = userService.getById(task.getAssignedTo());
                fullName = userDto.getFullName();
            } catch (Exception e) {
                fullName = "User không tồn tại";
            }
        }

        // 3. Trả về Response kèm tên
        return TaskMapper.toResponse(task, fullName);
    }

    @Override
    public void deleteTask(Long id) {
        // 1. Kiểm tra xem Task có tồn tại trong DB không
        if (!taskRepository.existsById(id)) {
            throw new EntityNotFoundException("Không thể xóa! Không tìm thấy Task với ID: " + id);
        }

        // 2. Nếu tồn tại thì gọi Repository để xóa
        taskRepository.deleteById(id);

    }

    @Override
    public List<TaskHistoryResponse> getTaskHistories(Long taskId) {

        List<TaskHistoryEntity> historyEntities = historyRepository.findByTaskIdOrderByCreatedAtDesc(taskId);

        // 2. Dùng Stream để lặp qua từng dòng lịch sử và biến nó thành Response DTO
        return historyEntities.stream().map(entity -> {
            TaskHistoryResponse response = new TaskHistoryResponse();
            response.setId(entity.getId());
            response.setTaskId(entity.getTaskId());
            response.setFieldName(entity.getFieldName());
            response.setOldValue(entity.getOldValue());
            response.setNewValue(entity.getNewValue());
            response.setCreatedAt(entity.getCreatedAt());

            try {
                var userDto = userService.getById(entity.getActorId());
                response.setActorName(userDto.getFullName());
            } catch (Exception e) {
                response.setActorName("Người dùng không tồn tại");
            }

            return response;
        }).toList(); // Gom tất cả lại thành 1 danh sách List
    }
}
