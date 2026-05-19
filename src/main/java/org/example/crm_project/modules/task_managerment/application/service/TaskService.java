package org.example.crm_project.modules.task_managerment.application.service;

import java.util.List;

import org.example.crm_project.modules.task_managerment.application.dto.request.CreateTaskRequest;
import org.example.crm_project.modules.task_managerment.application.dto.request.UpdateTaskRequest;
import org.example.crm_project.modules.task_managerment.application.dto.response.TaskHistoryResponse;
import org.example.crm_project.modules.task_managerment.application.dto.response.TaskResponse;
import org.springframework.data.domain.Page;

public interface TaskService {
    TaskResponse createTask(CreateTaskRequest request);
    TaskResponse updateTask(Long id, UpdateTaskRequest request);
    
    TaskResponse getTaskById(Long id);
    void deleteTask(Long id);
    
    Page<TaskResponse> getAllTasks(String subject, String status, String priority, int page, int size);
    
    List<TaskHistoryResponse> getTaskHistories(Long taskId);
}
