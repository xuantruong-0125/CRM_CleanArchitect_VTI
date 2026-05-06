package org.example.crm_project.modules.task_managerment.application.service;

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
import org.example.crm_project.modules.task_managerment.infrastructure.persistence.entity.TaskJpaEntity;
import org.example.crm_project.modules.task_managerment.infrastructure.persistence.mapper.TaskJpaMapper;
import org.example.crm_project.modules.task_managerment.infrastructure.persistence.repository.JpaTaskRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final JpaTaskRepository jpaRepository;

    private final UserService userService;

    @Override
    public Page<TaskResponse> getAllTasks(String subject, String status, String priority,int page, int size) {
        // Tạo đối tượng yêu cầu phân trang của Spring
        Pageable pageable = PageRequest.of(page, size);


        // Chuyển đổi String từ Frontend thành Enum của Backend (Nếu có truyền)
        TaskStatus taskStatus = (status != null && !status.isEmpty()) ? TaskStatus.valueOf(status) : null;
        TaskPriority taskPriority = (priority != null && !priority.isEmpty()) ? TaskPriority.valueOf(priority) : null;
        
        // Lên Database lấy dữ liệu (Nó sẽ tự động trả về cục Page chứa Entity)
        Page<TaskJpaEntity> jpaPage = jpaRepository.searchTasks(subject, taskStatus, taskPriority, pageable);

        return jpaPage.map(jpa -> {
            Task domain = TaskJpaMapper.toDomain(jpa);

            // GỌI SANG MODULE USER ĐỂ LẤY THÔNG TIN
            String fullName = null;
            if (domain.getAssignedTo() != null) {
                try {
                    // UserService có hàm getById trả về Object chứa fullName
                    var userDto = userService.getById(domain.getAssignedTo());
                    fullName = userDto.getFullName();
                } catch (Exception e) {
                    // Xử lý lỡ user bị xóa mất khỏi DB
                    fullName = "User không tồn tại";
                }
            }

            return TaskMapper.toResponse(domain, fullName);
        });
    }

    @Override
    public TaskResponse createTask(CreateTaskRequest request, Long assignedByUserId) {
        // TODO: Viết logic tạo mới sau
        return null;
    }

    @Override
    public TaskResponse updateTask(Long id, UpdateTaskRequest request) {
        // TODO: Viết logic cập nhật sau
        return null;
    }

    @Override
    public TaskResponse getTaskById(Long id) {
        // TODO: Viết logic lấy chi tiết sau
        return null;
    }

    @Override
    public void deleteTask(Long id) {
        // TODO: Viết logic xóa sau
    }

}
