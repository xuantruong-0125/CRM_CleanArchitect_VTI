package org.example.crm_project.modules.contacts_managerment.application.dto.response;

import lombok.Builder;
import lombok.Data;
import org.example.crm_project.modules.contacts_managerment.domain.entity.CustomerType;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
public class CustomerResponse {
    private Long id;
    private Long parentId;
    private String customerCode;
    private CustomerType type;
    private String name;
    private String shortName;
    private String taxCode;
    private String phone;
    private String email;
    private String fax;
    private LocalDate establishedDate;
    private String description;
    private Long sourceId;
    private Long statusId;
    private Long tierId;
    private Long assignedTo;
    private java.util.List<ContactResponse> contacts;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
