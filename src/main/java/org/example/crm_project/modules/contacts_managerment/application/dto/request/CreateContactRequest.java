package org.example.crm_project.modules.contacts_managerment.application.dto.request;

import lombok.Data;
import java.time.LocalDate;

@Data
public class CreateContactRequest {
    private String fullName;
    private String position;
    private String phone;
    private String email;
    private String address;
    private LocalDate dob;
    private String notes;
    private boolean isPrimary;
    private boolean isActive;
}
