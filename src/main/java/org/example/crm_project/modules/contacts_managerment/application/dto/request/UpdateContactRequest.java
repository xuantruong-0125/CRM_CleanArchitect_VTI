package org.example.crm_project.modules.contacts_managerment.application.dto.request;

import lombok.Data;
import java.time.LocalDate;

@Data
public class UpdateContactRequest {
    private String fullName;
    private String position;
    private String phone;
    private String email;
    private String address;
    private LocalDate dob;
    private String notes;
    @com.fasterxml.jackson.annotation.JsonProperty("isPrimary")
    private boolean isPrimary;
    @com.fasterxml.jackson.annotation.JsonProperty("isActive")
    private boolean isActive;
    private Long customerId;
}
