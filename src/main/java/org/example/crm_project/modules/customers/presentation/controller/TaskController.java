package org.example.crm_project.modules.customers.presentation.controller;

import org.example.crm_project.modules.customers.application.dto.request.CreateTaskDTO;
import org.example.crm_project.modules.customers.application.dto.response.TaskResponseDTO;
import org.example.crm_project.modules.customers.application.service.TaskService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

/**
 * Controller: TaskController
 * REST endpoints for Task management
 */
@RestController
@RequestMapping("/api/tasks")
@Validated
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public ResponseEntity<TaskResponseDTO> createTask(@Valid @RequestBody CreateTaskDTO createDTO) {
        TaskResponseDTO created = taskService.createTask(createDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponseDTO> getTaskById(@PathVariable Long id) {
        TaskResponseDTO task = taskService.getTaskById(id);
        return ResponseEntity.ok(task);
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<Page<TaskResponseDTO>> getTasksByCustomer(
            @PathVariable Long customerId,
            Pageable pageable) {
        Page<TaskResponseDTO> tasks = taskService.getTasksByCustomer(customerId, pageable);
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<Page<TaskResponseDTO>> getTasksByAssignedUser(
            @PathVariable Long userId,
            Pageable pageable) {
        Page<TaskResponseDTO> tasks = taskService.getTasksByAssignedUser(userId, pageable);
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<Page<TaskResponseDTO>> getTasksByStatus(
            @PathVariable String status,
            Pageable pageable) {
        Page<TaskResponseDTO> tasks = taskService.getTasksByStatus(status, pageable);
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/priority/{priority}")
    public ResponseEntity<Page<TaskResponseDTO>> getTasksByPriority(
            @PathVariable String priority,
            Pageable pageable) {
        Page<TaskResponseDTO> tasks = taskService.getTasksByPriority(priority, pageable);
        return ResponseEntity.ok(tasks);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskResponseDTO> updateTask(
            @PathVariable Long id,
            @Valid @RequestBody CreateTaskDTO createDTO) {
        TaskResponseDTO updated = taskService.updateTask(id, createDTO);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/count")
    public ResponseEntity<Long> countTasks() {
        long count = taskService.countTasks();
        return ResponseEntity.ok(count);
    }
}
