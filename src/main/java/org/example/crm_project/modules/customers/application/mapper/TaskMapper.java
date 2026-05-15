package org.example.crm_project.modules.customers.application.mapper;

import org.example.crm_project.modules.customers.application.dto.request.CreateTaskDTO;
import org.example.crm_project.modules.customers.application.dto.response.TaskResponseDTO;
import org.example.crm_project.modules.customers.domain.entity.Task;
import org.springframework.stereotype.Component;

@Component
public class TaskMapper {

    public Task toEntity(CreateTaskDTO dto) {
        if (dto == null) {
            return null;
        }
        Task task = new Task();
        task.setSubject(dto.getSubject());
        task.setDescription(dto.getDescription());
        task.setStartDate(dto.getStartDate());
        task.setDueDate(dto.getDueDate());
        task.setCompletedAt(dto.getCompletedAt());
        task.setStatus(dto.getStatus());
        task.setPriority(dto.getPriority());
        task.setProgressPercent(dto.getProgressPercent());
        task.setRelatedToType(dto.getRelatedToType());
        task.setRelatedToId(dto.getRelatedToId());
        task.setAssignedTo(dto.getAssignedTo());
        task.setAssignedBy(dto.getAssignedBy());
        task.setContactId(dto.getContactId());
        return task;
    }

    public TaskResponseDTO toResponseDTO(Task entity) {
        if (entity == null) {
            return null;
        }
        TaskResponseDTO dto = new TaskResponseDTO();
        dto.setId(entity.getId());
        dto.setSubject(entity.getSubject());
        dto.setDescription(entity.getDescription());
        dto.setStartDate(entity.getStartDate());
        dto.setDueDate(entity.getDueDate());
        dto.setCompletedAt(entity.getCompletedAt());
        dto.setStatus(entity.getStatus());
        dto.setPriority(entity.getPriority());
        dto.setProgressPercent(entity.getProgressPercent());
        dto.setRelatedToType(entity.getRelatedToType());
        dto.setRelatedToId(entity.getRelatedToId());
        dto.setAssignedTo(entity.getAssignedTo());
        dto.setAssignedBy(entity.getAssignedBy());
        dto.setContactId(entity.getContactId());
        dto.setCreatedBy(entity.getCreatedBy());
        dto.setUpdatedBy(entity.getUpdatedBy());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        return dto;
    }

    public void updateEntityFromDTO(Task task, CreateTaskDTO dto) {
        if (dto == null || task == null) {
            return;
        }
        if (dto.getSubject() != null) task.setSubject(dto.getSubject());
        if (dto.getDescription() != null) task.setDescription(dto.getDescription());
        if (dto.getStartDate() != null) task.setStartDate(dto.getStartDate());
        if (dto.getDueDate() != null) task.setDueDate(dto.getDueDate());
        if (dto.getCompletedAt() != null) task.setCompletedAt(dto.getCompletedAt());
        if (dto.getStatus() != null) task.setStatus(dto.getStatus());
        if (dto.getPriority() != null) task.setPriority(dto.getPriority());
        if (dto.getProgressPercent() != null) task.setProgressPercent(dto.getProgressPercent());
        if (dto.getRelatedToType() != null) task.setRelatedToType(dto.getRelatedToType());
        if (dto.getRelatedToId() != null) task.setRelatedToId(dto.getRelatedToId());
        if (dto.getAssignedTo() != null) task.setAssignedTo(dto.getAssignedTo());
        if (dto.getAssignedBy() != null) task.setAssignedBy(dto.getAssignedBy());
        if (dto.getContactId() != null) task.setContactId(dto.getContactId());
    }
}
