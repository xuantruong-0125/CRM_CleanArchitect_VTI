package org.example.crm_project.modules.leads.presentation.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class LeadErrorResponse {

    private int status;
    private String errorCode;
    private String message;
    private String path;
    private LocalDateTime timestamp;
}