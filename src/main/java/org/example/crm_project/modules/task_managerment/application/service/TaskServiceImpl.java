package org.example.crm_project.modules.task_managerment.application.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.example.crm_project.modules.system_managerment.application.service.UserService;
import org.example.crm_project.modules.task_managerment.application.dto.request.CreateTaskRequest;
import org.example.crm_project.modules.task_managerment.application.dto.request.UpdateTaskRequest;
import org.example.crm_project.modules.task_managerment.application.dto.response.TaskResponse;
import org.example.crm_project.modules.task_managerment.application.mapper.TaskMapper;
import org.example.crm_project.modules.task_managerment.domain.constant.TaskPriority;
import org.example.crm_project.modules.task_managerment.domain.constant.TaskStatus;
import org.example.crm_project.modules.task_managerment.domain.entity.Task;
import org.example.crm_project.modules.task_managerment.domain.repository.TaskRepository;
import org.example.crm_project.modules.task_managerment.infrastructure.persistence.entity.TaskJpaEntity;
import org.example.crm_project.modules.task_managerment.infrastructure.persistence.mapper.TaskJpaMapper;
import org.example.crm_project.modules.task_managerment.infrastructure.persistence.repository.JpaTaskRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final UserService userService;
    private final TaskRepository taskRepository;

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
    public TaskResponse updateTask(Long id, UpdateTaskRequest request) {
        // TODO: Viết logic cập nhật sau
        return null;
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

}
