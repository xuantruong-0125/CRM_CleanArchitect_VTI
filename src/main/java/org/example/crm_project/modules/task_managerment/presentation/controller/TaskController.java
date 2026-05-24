package org.example.crm_project.modules.task_managerment.presentation.controller;

import java.util.List;

import org.example.crm_project.modules.task_managerment.application.dto.request.CreateTaskRequest;
import org.example.crm_project.modules.task_managerment.application.dto.request.UpdateTaskRequest;
import org.example.crm_project.modules.task_managerment.application.dto.response.TaskHistoryResponse;
import org.example.crm_project.modules.task_managerment.application.dto.response.TaskResponse;
import org.example.crm_project.modules.task_managerment.application.service.TaskService;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @GetMapping
    @PreAuthorize("hasAuthority('TASK_VIEW')")
    public ResponseEntity<Page<TaskResponse>> getAllTasks(
            @RequestParam(required = false) String subject,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String priority,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<TaskResponse> responses = taskService.getAllTasks(subject, status, priority, page, size);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('TASK_VIEW')")
    public ResponseEntity<TaskResponse> getTaskById(@PathVariable Long id) {

        // Gọi Service lấy dữ liệu
        TaskResponse response = taskService.getTaskById(id);

        // Trả về JSON với Status 200 (OK)
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('TASK_DELETE')")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        // Gọi xuống Service để xử lý logic xóa
        taskService.deleteTask(id);

        // Trả về HTTP Status 204 (No Content)
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    @PreAuthorize("hasAuthority('TASK_CREATE')")
    public ResponseEntity<TaskResponse> createTask(@RequestBody CreateTaskRequest request) {
        
        TaskResponse response = taskService.createTask(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAuthority('TASK_UPDATE')")
    public ResponseEntity<TaskResponse> updateTask(
            @PathVariable Long id,
            @RequestBody UpdateTaskRequest request) {

        // Gọi Service xử lý
        TaskResponse response = taskService.updateTask(id, request);

        // Trả về dữ liệu mới nhất cho Frontend cập nhật UI
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}/histories")
    @PreAuthorize("hasAuthority('TASK_VIEW')")
    public ResponseEntity<List<TaskHistoryResponse>> getTaskHistories(@PathVariable Long id) {
        List<TaskHistoryResponse> histories = taskService.getTaskHistories(id);
        return ResponseEntity.ok(histories);
    }
}
