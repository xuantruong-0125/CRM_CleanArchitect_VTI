package org.example.crm_project.modules.contacts_managerment.application.dto.request;

import lombok.Data;
import org.example.crm_project.modules.contacts_managerment.domain.entity.CustomerType;

import java.time.LocalDate;

@Data
public class CreateCustomerRequest {
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
}
