package org.example.crm_project.modules.activity_management.application.dto.request;

import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import org.example.crm_project.modules.activity_management.domain.constant.ActivityType;
import java.time.LocalDateTime;

@Data
public class CreateActivityRequest {
    @NotNull(message = "Loại hoạt động không được để trống")
    private ActivityType activityType;
    
    @NotBlank(message = "Tiêu đề không được để trống")
    private String subject;
    
    private String description;   
    private String relatedToType; 
    private Long relatedToId;     
    
    @NotNull(message = "Người thực hiện không được để trống")
    private Long performedBy;
    
    @NotNull(message = "Ngày bắt đầu không được để trống")
    private LocalDateTime startDate;
    
    private LocalDateTime endDate;


    private String noteContent;
}