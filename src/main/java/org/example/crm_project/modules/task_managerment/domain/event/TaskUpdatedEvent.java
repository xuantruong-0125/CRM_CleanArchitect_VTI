package org.example.crm_project.modules.task_managerment.domain.event;

public record TaskUpdatedEvent(
    Long taskId,
    Long actorId,      // Lấy từ Spring Security lúc update
    String fieldName,  // Ví dụ: "status"
    String oldValue,   // Ví dụ: "TODO"
    String newValue    // Ví dụ: "IN_PROGRESS"
) {

}
