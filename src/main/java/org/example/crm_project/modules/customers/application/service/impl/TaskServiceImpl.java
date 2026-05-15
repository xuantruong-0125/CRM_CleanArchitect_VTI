package org.example.crm_project.modules.customers.application.service.impl;

import org.example.crm_project.modules.customers.application.dto.request.CreateTaskDTO;
import org.example.crm_project.modules.customers.application.dto.response.TaskResponseDTO;
import org.example.crm_project.modules.customers.application.mapper.TaskMapper;
import org.example.crm_project.modules.customers.application.service.TaskService;
import org.example.crm_project.modules.customers.domain.entity.Task;
import org.example.crm_project.modules.customers.domain.repository.TaskRepository;
import org.example.crm_project.modules.customers.domain.exception.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation: TaskServiceImpl
 * Business logic for Task management
 */
@Service
@Transactional
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    public TaskServiceImpl(TaskRepository taskRepository, TaskMapper taskMapper) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
    }

    @Override
    public TaskResponseDTO createTask(CreateTaskDTO createDTO) {
        Task task = taskMapper.toEntity(createDTO);
        Task saved = taskRepository.save(task);
        return taskMapper.toResponseDTO(saved);
    }

    @Override
    public TaskResponseDTO getTaskById(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tác vụ không tìm thấy: " + id));
        return taskMapper.toResponseDTO(task);
    }

    @Override
    public Page<TaskResponseDTO> getTasksByCustomer(Long customerId, Pageable pageable) {
        Page<Task> tasks = taskRepository.findByCustomerId(customerId, pageable);
        return tasks.map(taskMapper::toResponseDTO);
    }

    @Override
    public Page<TaskResponseDTO> getTasksByAssignedUser(Long userId, Pageable pageable) {
        Page<Task> tasks = taskRepository.findByAssignedTo(userId, pageable);
        return tasks.map(taskMapper::toResponseDTO);
    }

    @Override
    public Page<TaskResponseDTO> getTasksByStatus(String status, Pageable pageable) {
        Page<Task> tasks = taskRepository.findByStatus(status, pageable);
        return tasks.map(taskMapper::toResponseDTO);
    }

    @Override
    public Page<TaskResponseDTO> getTasksByPriority(String priority, Pageable pageable) {
        Page<Task> tasks = taskRepository.findByPriority(priority, pageable);
        return tasks.map(taskMapper::toResponseDTO);
    }

    @Override
    public TaskResponseDTO updateTask(Long id, CreateTaskDTO createDTO) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tác vụ không tìm thấy: " + id));
        taskMapper.updateEntityFromDTO(task, createDTO);
        Task updated = taskRepository.save(task);
        return taskMapper.toResponseDTO(updated);
    }

    @Override
    public void deleteTask(Long id) {
        if (!taskRepository.existsById(id)) {
            throw new EntityNotFoundException("Tác vụ không tìm thấy: " + id);
        }
        taskRepository.delete(id);
    }

    @Override
    @Transactional(readOnly = true)
    public long countTasks() {
        return taskRepository.count();
    }
}
