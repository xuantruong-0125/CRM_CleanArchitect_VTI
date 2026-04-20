package org.example.crm_project.modules.leads.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConvertLeadResponse {

    private Long leadId;
    private Long customerId;
    private Long contactId;
    private Long opportunityId;
    private LocalDateTime convertedAt;
}