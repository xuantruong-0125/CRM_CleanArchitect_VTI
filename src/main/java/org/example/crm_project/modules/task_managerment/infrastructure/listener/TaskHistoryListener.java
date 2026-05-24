package org.example.crm_project.modules.task_managerment.infrastructure.listener;

import org.example.crm_project.modules.task_managerment.domain.event.TaskUpdatedEvent;
import org.example.crm_project.modules.task_managerment.infrastructure.persistence.entity.TaskHistoryEntity;
import org.example.crm_project.modules.task_managerment.infrastructure.persistence.repository.JpaTaskHistoryRepository;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TaskHistoryListener {
    private final JpaTaskHistoryRepository historyRepository;

    @EventListener
    public void handleTaskUpdated(TaskUpdatedEvent event) {
        // Chuyển đổi từ Event sang Entity
        TaskHistoryEntity history = new TaskHistoryEntity();
        history.setTaskId(event.taskId());
        history.setActorId(event.actorId());
        history.setFieldName(event.fieldName());
        history.setOldValue(event.oldValue());
        history.setNewValue(event.newValue());

        // Lưu xuống DB
        historyRepository.save(history);
        
        System.out.println(">>> [Hệ thống] Đã ghi nhận lịch sử thay đổi cho Task ID: " + event.taskId());
    }

}
