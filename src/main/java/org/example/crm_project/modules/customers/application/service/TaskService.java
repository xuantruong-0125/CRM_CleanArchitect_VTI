package org.example.crm_project.modules.customers.application.service;

import org.example.crm_project.modules.customers.application.dto.request.CreateTaskDTO;
import org.example.crm_project.modules.customers.application.dto.response.TaskResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TaskService {

    TaskResponseDTO createTask(CreateTaskDTO createDTO);

    TaskResponseDTO getTaskById(Long id);

    Page<TaskResponseDTO> getTasksByCustomer(Long customerId, Pageable pageable);

    Page<TaskResponseDTO> getTasksByAssignedUser(Long userId, Pageable pageable);

    Page<TaskResponseDTO> getTasksByStatus(String status, Pageable pageable);

    Page<TaskResponseDTO> getTasksByPriority(String priority, Pageable pageable);

    TaskResponseDTO updateTask(Long id, CreateTaskDTO createDTO);

    void deleteTask(Long id);

    long countTasks();
}
