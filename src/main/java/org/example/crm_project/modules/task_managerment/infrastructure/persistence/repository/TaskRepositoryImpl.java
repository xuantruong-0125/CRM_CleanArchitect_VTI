package org.example.crm_project.modules.task_managerment.infrastructure.persistence.repository;

import java.time.LocalDateTime;
import java.util.Optional;

import org.example.crm_project.modules.task_managerment.domain.constant.TaskPriority;
import org.example.crm_project.modules.task_managerment.domain.constant.TaskStatus;
import org.example.crm_project.modules.task_managerment.domain.entity.Task;
import org.example.crm_project.modules.task_managerment.domain.repository.TaskRepository;
import org.example.crm_project.modules.task_managerment.infrastructure.persistence.entity.TaskJpaEntity;
import org.example.crm_project.modules.task_managerment.infrastructure.persistence.mapper.TaskJpaMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TaskRepositoryImpl implements TaskRepository {
    
    // Ở Adapter mới được phép gọi JPA
    private final JpaTaskRepository jpaRepository; 

    @Override
    public Optional<Task> findById(Long id) {
        return jpaRepository.findById(id).map(TaskJpaMapper::toDomain);
    }

    // THỰC THI HÀM PHÂN TRANG Ở ĐÂY
    @Override
    public Page<Task> searchTasks(String subject, TaskStatus status, TaskPriority priority,LocalDateTime fromDateTime,LocalDateTime toDateTime,Long currentUserId, Long organizationId,String scope, Pageable pageable) {
        
        // 1. Gọi JPA lấy lên cục Page chứa Entity
        Page<TaskJpaEntity> jpaPage = jpaRepository.searchTasks(subject, status, priority, fromDateTime, toDateTime, currentUserId, organizationId, scope, pageable);
        
        // 2. Map TẤT CẢ Entity trong Page đó sang Domain Model (Task)
        return jpaPage.map(TaskJpaMapper::toDomain);
    }

    @Override
    public Task save(Task task) {
        // Chuyển từ Domain thuần -> Entity của JPA
        TaskJpaEntity entity = TaskJpaMapper.toJpa(task);
        
        // Lưu xuống DB
        TaskJpaEntity savedEntity = jpaRepository.save(entity);
        
        // Chuyển ngược lại kết quả từ Entity -> Domain để trả về Service
        return TaskJpaMapper.toDomain(savedEntity);
    }
  
    // 3. Ghi đè hàm XÓA
    @Override
    public void deleteById(Long id) {
        // Trực tiếp gọi hàm xóa của Spring Data JPA
        jpaRepository.deleteById(id);
    }

    // 4. Ghi đè hàm KIỂM TRA TỒN TẠI
    @Override
    public boolean existsById(Long id) {
        // Trực tiếp gọi hàm kiểm tra của Spring Data JPA
        return jpaRepository.existsById(id);
    }
}
